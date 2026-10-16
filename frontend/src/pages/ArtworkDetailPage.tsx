import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import AIChat from "../components/AIChat";

import { getArtworkById } from "../api/artworks";
import type { Artwork } from "../types/artwork";

export default function ArtworkDetailPage() {
  const { artworkId } = useParams();

  const [artwork, setArtwork] = useState<Artwork | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [imageFailed, setImageFailed] = useState(false);

  useEffect(() => {
    if (!artworkId) {
      setError("Artwork ID is missing.");
      setLoading(false);
      return;
    }

    let cancelled = false;

    async function loadArtwork(id: string) {
      try {
        setLoading(true);
        setError(null);
        setArtwork(null);
        setImageFailed(false);

        const data = await getArtworkById(id);

        if (!cancelled) {
          setArtwork(data);
        }
      } catch (err) {
        if (!cancelled) {
          setError(
            err instanceof Error
              ? err.message
              : "Failed to load artwork."
          );
        }
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    }

    loadArtwork(artworkId);

    return () => {
      cancelled = true;
    };
  }, [artworkId]);

  if (loading) {
    return (
      <main style={{ padding: "160px 32px" }}>
        Loading artwork...
      </main>
    );
  }

  if (error || !artwork) {
    return (
      <main style={{ padding: "160px 32px" }}>
        {error || "Artwork not found."}
      </main>
    );
  }

  return (
    <main
      style={{
        minHeight: "100vh",
        maxWidth: "1280px",
        margin: "0 auto",
        padding: "150px 32px 100px",
      }}
    >
      <Link
        to={`/exhibitions/${artwork.exhibitionId}`}
        style={{
          color: "var(--color-accent)",
          fontSize: "12px",
          letterSpacing: "0.12em",
        }}
      >
        ← BACK TO EXHIBITION
      </Link>

      <section
        style={{
          display: "grid",
          gridTemplateColumns:
            "repeat(auto-fit, minmax(min(100%, 400px), 1fr))",
          gap: "64px",
          marginTop: "60px",
          alignItems: "center",
        }}
      >
        {/* Artwork Image */}
        <div
  style={{
    width: "100%",
    background: "transparent",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  }}
>
          {artwork.imageUrl && !imageFailed ? (
            <img
            src={artwork.imageUrl}
            alt={artwork.title}
            onError={() => setImageFailed(true)}
            style={{
              display: "block",
              width: "100%",
              height: "auto",
              maxHeight: "75vh",
              objectFit: "contain",
            }}
          />
          ) : (
            <span
              style={{
                color: "var(--color-muted)",
                fontSize: "12px",
                letterSpacing: "0.15em",
              }}
            >
              IMAGE UNAVAILABLE
            </span>
          )}
        </div>

        {/* Artwork Information */}
        <div>
          <p
            style={{
              color: "var(--color-accent)",
              fontSize: "11px",
              letterSpacing: "0.2em",
              textTransform: "uppercase",
            }}
          >
            MUSEFLOW / ARTWORK
          </p>

          <h1
            style={{
              marginTop: "24px",
              fontSize: "clamp(56px, 7vw, 100px)",
              lineHeight: 0.95,
            }}
          >
            {artwork.title}
          </h1>

          <h2
            style={{
              marginTop: "32px",
              fontSize: "32px",
              color: "var(--color-accent)",
            }}
          >
            {artwork.artist}
          </h2>

          <div
            style={{
              marginTop: "36px",
              paddingTop: "24px",
              borderTop: "1px solid var(--color-border)",
              color: "var(--color-muted)",
              fontSize: "13px",
            }}
          >
            {artwork.yearCreated != null && (
              <p>YEAR — {artwork.yearCreated}</p>
            )}

            {artwork.medium && (
              <p>MEDIUM — {artwork.medium}</p>
            )}
          </div>

          {artwork.description && (
            <p
              style={{
                marginTop: "32px",
                maxWidth: "500px",
                color: "var(--color-text)",
                fontSize: "15px",
                lineHeight: 1.9,
              }}
            >
              {artwork.description}
            </p>
          )}
        </div>
      </section>
      
      <AIChat
  key={artwork.id}
  artworkId={artwork.id}
  artworkTitle={artwork.title}
/>
    </main>
  );
}