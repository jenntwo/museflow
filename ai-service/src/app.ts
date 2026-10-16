import express from "express";
import cors from "cors";

import chatRouter from "./routes/chat.js";

const app = express();

app.use(cors());
app.use(express.json());

app.get("/api/v1/ai/health", (_req, res) => {
  res.json({
    status: "UP",
    service: "MuseFlow AI Curator",
    version: "1.0.0",
  });
});

app.use("/api/v1/ai", chatRouter);

export default app;