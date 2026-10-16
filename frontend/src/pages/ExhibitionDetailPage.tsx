import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

import { getExhibitionById } from "../api/exhibitions";
import { getArtworksByExhibition } from "../api/artworks";

import type { Exhibition } from "../types/exhibition";
import type { Artwork } from "../types/artwork";

import ArtworkCard from "../components/ArtworkCard";

export default function ExhibitionDetailPage() {
  const { exhibitionId } = useParams();

  const [exhibition, setExhibition] = useState<Exhibition | null>(null);
  const [artworks, setArtworks] = useState<Artwork[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!exhibitionId) {
      setError("Exhibition ID is missing.");
      setLoading(false);
      return;
    }

    let cancelled = false;

    async function loadExhibition(id: string) {
      try {
        setLoading(true);
        setError(null);

        const [exhibitionData, artworkData] = await Promise.all([
          getExhibitionById(id),
          getArtworksByExhibition(id),
        ]);

        if (!cancelled) {
          setExhibition(exhibitionData);
          setArtworks(artworkData);
        }
      } catch (err) {
        if (!cancelled) {
          setError(
            err instanceof Error
              ? err.message
              : "Failed to load exhibition."
          );
        }
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    }

    loadExhibition(exhibitionId);

    return () => {
      cancelled = true;
    };
  }, [exhibitionId]);

  if (loading) {
    return (
      <main style={{ padding: "160px 32px" }}>
        Loading exhibition...
      </main>
    );
  }

  if (error) {
    return (
      <main style={{ padding: "160px 32px" }}>
        {error}
      </main>
    );
  }

  if (!exhibition) {
    return (
      <main style={{ padding: "160px 32px" }}>
        Exhibition not found.
      </main>
    );
  }

  return (
    <main
      style={{
        minHeight: "100vh",
        padding: "160px 32px",
        maxWidth: "1280px",
        margin: "0 auto",
      }}
    >
      {/* Exhibition Header */}
      <p
        style={{
          color: "var(--color-accent)",
          letterSpacing: "0.2em",
          fontSize: "11px",
        }}
      >
        MUSEFLOW / EXHIBITION
      </p>

      <h1
        style={{
          marginTop: "24px",
          fontSize: "clamp(56px, 8vw, 110px)",
        }}
      >
        {exhibition.title}
      </h1>

      <p
        style={{
          marginTop: "24px",
          color: "var(--color-accent)",
        }}
      >
        {exhibition.startDate} — {exhibition.endDate}
      </p>

      {exhibition.description && (
        <p
          style={{
            marginTop: "32px",
            maxWidth: "650px",
            color: "var(--color-muted)",
          }}
        >
          {exhibition.description}
        </p>
      )}

      {/* Artwork Gallery */}
      <section style={{ marginTop: "110px" }}>
        <p
          style={{
            color: "var(--color-accent)",
            fontSize: "11px",
            letterSpacing: "0.2em",
          }}
        >
          01 / THE COLLECTION
        </p>

        <h2
          style={{
            marginTop: "16px",
            fontSize: "clamp(48px, 6vw, 80px)",
          }}
        >
          Explore the Artworks
        </h2>

        {artworks.length === 0 ? (
          <p
            style={{
              marginTop: "40px",
              color: "var(--color-muted)",
            }}
          >
            No artworks available for this exhibition.
          </p>
        ) : (
          <div
            style={{
              display: "grid",
              gridTemplateColumns:
                "repeat(auto-fit, minmax(min(100%, 280px), 1fr))",
              gap: "40px",
              marginTop: "50px",
            }}
          >
            {artworks.map((artwork) => (
              <ArtworkCard
                key={artwork.id}
                artwork={artwork}
              />
            ))}
          </div>
        )}
      </section>

      {/* Back Navigation */}
      <Link
        to={`/museums/${exhibition.museumId}`}
        style={{
          display: "inline-block",
          marginTop: "80px",
          color: "var(--color-accent)",
        }}
      >
        ← BACK TO MUSEUM
      </Link>
    </main>
  );
}