import {
  BrowserRouter,
  Route,
  Routes,
} from "react-router-dom";

import Navbar from "./components/Navbar";
import Hero from "./components/Hero";
import MuseumsPage from "./pages/MuseumsPage";
import MuseumDetailPage from "./pages/MuseumDetailPage";
import ExhibitionDetailPage from "./pages/ExhibitionDetailPage";
import ArtworkDetailPage from "./pages/ArtworkDetailPage";

function HomePage() {
  return (
    <>
      <Hero />
      <MuseumsPage />
    </>
  );
}

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<HomePage />} />

        <Route
          path="/museums/:museumId"
          element={<MuseumDetailPage />}
        />

<Route
  path="/exhibitions/:exhibitionId"
  element={<ExhibitionDetailPage />}
/>
<Route
  path="/artworks/:artworkId"
  element={<ArtworkDetailPage />}
/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;