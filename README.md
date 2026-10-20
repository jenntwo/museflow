# MuseFlow 🎨

**An AI-Powered Museum Guide Platform**

MuseFlow is a full-stack web application that allows visitors to explore museums, discover exhibitions, learn about artworks, and interact with an AI-powered art guide.

The platform combines a React frontend, a Java Spring Boot backend, a Node.js AI service, MongoDB Atlas, and OpenAI to deliver an interactive museum exploration experience.

### 🌐 Live Demo

**[Explore MuseFlow](https://museflow-three.vercel.app/)**

### ✨ Key Features

- **Museum Discovery:** Browse museums and explore their collections.
- **Exhibition Exploration:** Navigate exhibitions within museums.
- **Artwork Details:** View artwork information, including artist, creation year, medium, and description.
- **AI Art Guide:** Ask natural-language questions about artworks and receive AI-generated explanations.
- **Cloud Deployment:** Access the application through publicly deployed frontend and backend services.

### 🛠 Tech Stack

| Layer           | Technologies                             |
| --------------- | ---------------------------------------- |
| Frontend        | React, TypeScript, Vite                  |
| Backend         | Java 21, Spring Boot 4, REST APIs        |
| AI Service      | Node.js, Express, TypeScript, OpenAI API |
| Database        | MongoDB, MongoDB Atlas                   |
| Testing         | JUnit, Spring MockMvc, Vitest            |
| Deployment      | Docker, Render, Vercel                   |
| Version Control | Git, GitHub                              |

### 🏗 System Architecture

```text
                 User / Browser
                       |
                       v
              React + TypeScript
                  (Vercel)
                       |
           +-----------+-----------+
           |                       |
           v                       v
    Spring Boot API          Express AI Service
       (Render)                  (Render)
           |                       |
           v                       +----> OpenAI API
     MongoDB Atlas                 |
                                   v
                            Spring Boot API
                                   |
                                   v
                             MongoDB Atlas
```

### 🔌 API Endpoints

**Spring Boot Backend**

| Method | Endpoint                | Description              |
| ------ | ----------------------- | ------------------------ |
| GET    | `/api/v1/museums`       | Retrieve museums         |
| GET    | `/api/v1/exhibitions`   | Retrieve exhibitions     |
| GET    | `/api/v1/artworks`      | Retrieve artworks        |
| GET    | `/api/v1/artworks/{id}` | Retrieve artwork details |

**AI Service**

| Method | Endpoint          | Description                               |
| ------ | ----------------- | ----------------------------------------- |
| POST   | `/api/v1/ai/chat` | Generate an AI-guided artwork explanation |

Example request:

```json
{
  "artworkId": "6ac84e1150f9972d7baa98d0",
  "message": "What makes The Starry Night so special?"
}
```

### 🚀 Deployment

MuseFlow is deployed across multiple cloud services:

- **Frontend:** Vercel
- **Java Backend:** Render (Docker)
- **AI Service:** Render (Node.js)
- **Database:** MongoDB Atlas

The application uses environment variables to manage database credentials, API keys, and service URLs.

### 💻 Local Development

**Prerequisites**

- Java 21
- Node.js and npm
- Docker
- MongoDB
- OpenAI API key for real AI responses

**1. Clone the repository**

```bash
git clone https://github.com/jenntwo/museflow.git
cd museflow
```

**2. Start MongoDB**

```bash
docker run -d \
  --name museflow-mongodb \
  -p 127.0.0.1:27017:27017 \
  -v museflow-mongodb-data:/data/db \
  mongo:7
```

**3. Start Spring Boot**

```bash
cd backend
./mvnw spring-boot:run
```

**4. Start AI Service**

Open another terminal:

```bash
cd ai-service
npm install
npm run dev
```

Configure the required environment variables before starting the AI service.

**5. Start React Frontend**

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

The frontend runs locally through Vite, with development API proxying to the backend services.

### 🔮 Future Improvements

- Retrieval-Augmented Generation (RAG) for source-grounded artwork explanations.
- Semantic search and vector embeddings.
- Expanded museum and artwork datasets.
- Improved monitoring, security, and AI cost management.
- Enhanced accessibility and mobile user experience.

### 👩‍💻 Author

**Jenny Ma**

[GitHub](https://github.com/jenntwo)

---

_Built as a full-stack software engineering and AI application development project._
