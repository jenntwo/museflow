import { useEffect, useState } from "react";
import { getMuseums } from "../api/museums";
import type { Museum } from "../types/museum";
import MuseumCard from "../components/MuseumCard";
import "./MuseumsPage.css";

export default function MuseumsPage() {
  const [museums, setMuseums] = useState<Museum[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    async function loadMuseums() {
      try {
        const data = await getMuseums();
        setMuseums(data);
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "Something went wrong"
        );
      } finally {
        setLoading(false);
      }
    }

    loadMuseums();
  }, []);

  if (loading) {
    return <p>Loading museums...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <main id="museums" className="museums-section">
      <div className="museums-container">
        <header className="museums-header">
          <div>
            <p className="museums-eyebrow">
              01 / DISCOVER
            </p>
  
            <h2 className="museums-title">
              Curated Spaces
            </h2>
          </div>
  
          <p className="museums-intro">
            Explore museums, exhibitions, and collections
            from spaces built to inspire.
          </p>
        </header>
  
        <div className="museums-divider" />
  
        {museums.length === 0 ? (
          <p className="museums-empty">
            No museums found.
          </p>
        ) : (
          <div className="museums-grid">
            {museums.map((museum, index) => (
              <MuseumCard
                key={museum.id}
                museum={museum}
                index={index}
              />
            ))}
          </div>
        )}
      </div>
    </main>
  );
}