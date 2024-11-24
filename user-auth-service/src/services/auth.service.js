const jwt = require('jsonwebtoken');
const { OAuth2Client } = require('google-auth-library');
const User = require('../models/user.model');

class AuthService {
  constructor() {
    this.googleClient = new OAuth2Client(process.env.GOOGLE_CLIENT_ID);
  }

  async googleAuth(token) {
    try {
      // Verify Google token
      const ticket = await this.googleClient.verifyIdToken({
        idToken: token,
        audience: process.env.GOOGLE_CLIENT_ID
      });
      const payload = ticket.getPayload();

      // Find or create user
      let user = await User.findOne({ email: payload.email });
      
      if (!user) {
        user = await User.create({
          email: payload.email,
          name: payload.name,
          picture: payload.picture,
          ssoProvider: 'google',
          ssoId: payload.sub,
          isVerified: true
        });
      }

      // Generate tokens
      const tokens = this.generateTokens(user._id);

      return { user, tokens };
    } catch (error) {
      throw error;
    }
  }

  generateTokens(userId) {
    const accessToken = jwt.sign(
      { userId },
      process.env.JWT_SECRET,
      { expiresIn: '15m' }
    );

    const refreshToken = jwt.sign(
      { userId },
      process.env.REFRESH_TOKEN_SECRET,
      { expiresIn: '7d' }
    );

    return { accessToken, refreshToken };
  }
}

module.exports = new AuthService();