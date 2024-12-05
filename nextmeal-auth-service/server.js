const express = require('express');
const bodyParser = require('body-parser');
const { Pool } = require('pg');
const cors = require('cors');
require('dotenv').config();

// Initialize Express app
const app = express();
const PORT = process.env.PORT;

// Enable CORS for frontend communication
app.use(cors());
app.use(bodyParser.json());

// PostgreSQL database configuration
const pool = new Pool({
  user: process.env.DB_USER ,
  host: process.env.DB_HOST ,
  database: process.env.DB_NAME ,
  password: process.env.DB_PASSWORD ,
  port: process.env.DB_PORT ,
  ssl: process.env.DB_SSL === 'true' ? { rejectUnauthorized: false } : false, // Enable SSL if DB_SSL=true
});

// Endpoint to check server health
app.get('/health', (req, res) => {
  res.send('Server is running healthy');
});

// Endpoint to insert or update user data
app.post('/api/users', async (req, res) => {
  const { user_id, name, email } = req.body;

  // Validate request body
  if (!user_id || !name || !email) {
    return res.status(400).json({ error: 'Missing user_id, name, or email' });
  }

  const query = `
    INSERT INTO users (user_id, name, email)
    VALUES ($1, $2, $3)
    ON CONFLICT (user_id) 
    DO UPDATE SET name = EXCLUDED.name, email = EXCLUDED.email
    RETURNING *;
  `;

  const values = [user_id, name, email];

  try {
    const result = await pool.query(query, values);
    res.status(201).json({
      message: 'User saved successfully',
      user: result.rows[0],
    });
  } catch (error) {
    console.error('Error saving user to database:', error);
    res.status(500).json({
      error: 'Failed to save user to database',
    });
  }
});

// Start the server
app.listen(PORT, async () => {
  try {
    await pool.connect();
    console.log('Connected to the database');
    console.log(`Server is running on http://localhost:${PORT}`);
  } catch (error) {
    console.error('Error connecting to the database:', error);
  }
});
