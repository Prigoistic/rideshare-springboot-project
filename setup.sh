#!/bin/bash

# RideShare Backend - Quick Setup Script
# This script helps you set up the environment and test the application

echo "🚀 RideShare Backend Setup Script"
echo "=================================="
echo ""

# Check if .env.properties exists
if [ ! -f .env.properties ]; then
    echo "⚠️  .env.properties not found!"
    echo ""
    echo "Creating .env.properties template..."
    cat > .env.properties << 'EOF'
SERVER_PORT=8081
SPRING_DATA_MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/rideshare
JWT_SECRET=change_this_to_a_strong_secret_key_at_least_256_bits_long
JWT_EXPIRATION=86400000
EOF
    echo "✅ Created .env.properties"
    echo ""
    echo "⚠️  IMPORTANT: Edit .env.properties and update:"
    echo "   - SPRING_DATA_MONGODB_URI with your MongoDB connection string"
    echo "   - JWT_SECRET with a strong secret key"
    echo ""
    exit 1
fi

echo "✅ Found .env.properties"
echo ""

# Build the project
echo "📦 Building project..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed!"
    exit 1
fi

echo "✅ Build successful!"
echo ""

# Start the application in background
echo "🚀 Starting application..."
./mvnw spring-boot:run &
APP_PID=$!

echo "⏳ Waiting for application to start (15 seconds)..."
sleep 15

# Test if application is running
if ! kill -0 $APP_PID 2>/dev/null; then
    echo "❌ Application failed to start!"
    exit 1
fi

echo "✅ Application started (PID: $APP_PID)"
echo ""

# Run basic tests
echo "🧪 Running basic API tests..."
echo ""

# Test 1: Register a user
echo "Test 1: Register user 'testuser'"
REGISTER_RESPONSE=$(curl -s -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123","role":"ROLE_USER"}')

if echo "$REGISTER_RESPONSE" | grep -q "User registered successfully"; then
    echo "✅ Registration successful"
else
    echo "⚠️  Registration response: $REGISTER_RESPONSE"
fi
echo ""

# Test 2: Login
echo "Test 2: Login as 'testuser'"
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}')

if echo "$LOGIN_RESPONSE" | grep -q "token"; then
    echo "✅ Login successful"
    TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"token":"[^"]*' | cut -d'"' -f4)
    echo "Token: ${TOKEN:0:50}..."
else
    echo "❌ Login failed: $LOGIN_RESPONSE"
fi
echo ""

# Test 3: Create ride (if we got a token)
if [ ! -z "$TOKEN" ]; then
    echo "Test 3: Create ride"
    RIDE_RESPONSE=$(curl -s -X POST http://localhost:8081/api/v1/rides \
      -H "Content-Type: application/json" \
      -H "Authorization: Bearer $TOKEN" \
      -d '{"pickupLocation":"123 Main St","dropLocation":"456 Park Ave"}')
    
    if echo "$RIDE_RESPONSE" | grep -q "REQUESTED"; then
        echo "✅ Ride created successfully"
        RIDE_ID=$(echo "$RIDE_RESPONSE" | grep -o '"id":"[^"]*' | cut -d'"' -f4)
        echo "Ride ID: $RIDE_ID"
    else
        echo "⚠️  Ride creation response: $RIDE_RESPONSE"
    fi
    echo ""
fi

# Summary
echo "=================================="
echo "📊 Setup Complete!"
echo "=================================="
echo ""
echo "Application is running on: http://localhost:8081"
echo "Application PID: $APP_PID"
echo ""
echo "📚 Next Steps:"
echo "1. Check PROJECT_DOCUMENTATION.md for complete documentation"
echo "2. Check API_TESTING_GUIDE.md for all API endpoints"
echo "3. Use the token above to test other endpoints"
echo ""
echo "To stop the application:"
echo "  kill $APP_PID"
echo ""
echo "To view logs:"
echo "  tail -f nohup.out"
echo ""
