
import { rateLimit } from "express-rate-limit";

export const aiRateLimiter = rateLimit({
  windowMs: 15 * 60 * 1000,

  limit: 10,

  standardHeaders: "draft-8",

  legacyHeaders: false,

  message: {
    error: "Too many AI requests. Please try again later.",
  },
});
