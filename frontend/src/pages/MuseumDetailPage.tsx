import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { getExhibitionsByMuseum } from "../api/exhibitions";
import type { Exhibition } from "../types/exhibition";
import { getMuseumById } from "../api/museums";
import type { Museum } from "../types/museum";

export default function MuseumDetailPage() {
  const { museumId } = useParams();
  const [exhibitions, setExhibitions] = useState<Exhibition[]>([]);
  const [museum, setMuseum] = useState<Museum | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!museumId) {
      setError("Museum ID is missing.");
      setLoading(false);
      return;
    }

    async function loadMuseum() {
      try {
        setLoading(true);

        const [museumData, exhibitionData] = await Promise.all([
          getMuseumById(museumId!),
          getExhibitionsByMuseum(museumId!),
        ]);
        
        setMuseum(museumData);
        setExhibitions(exhibitionData);
      } catch (err) {
        setError(
          err instanceof Error
            ? err.message
            : "Failed to load museum."
        );
      } finally {
        setLoading(false);
      }
    }

    loadMuseum();
  }, [museumId]);

  if (loading) {
    return <main>Loading museum...</main>;
  }

  if (error) {
    return <main>{error}</main>;
  }

  if (!museum) {
    return <main>Museum not found.</main>;
  }

  return (
    <main
      style={{
        minHeight: "100vh",
        padding: "160px 32px",
      }}
    >
      <p
        style={{
          color: "var(--color-accent)",
          letterSpacing: "0.2em",
          fontSize: "10px",
        }}
      >
        {museum.city} · {museum.country}
      </p>

      <h1
        style={{
          marginTop: "24px",
          fontSize: "clamp(64px, 9vw, 120px)",
        }}
      >
        {museum.name}
      </h1>

      {museum.description && (
        <p
          style={{
            maxWidth: "600px",
            color: "var(--color-muted)",
          }}
        >
          {museum.description}
        </p>
      )}
      <section
  style={{
    marginTop: "100px",
    maxWidth: "1000px",
  }}
>
  <p
    style={{
      color: "var(--color-accent)",
      fontSize: "10px",
      letterSpacing: "0.2em",
    }}
  >
    CURRENT EXHIBITIONS
  </p>

  {exhibitions.length === 0 ? (
    <p
      style={{
        marginTop: "30px",
        color: "var(--color-muted)",
      }}
    >
      No exhibitions available.
    </p>
  ) : (
    exhibitions.map((exhibition, index) => (
      <Link
        key={exhibition.id}
        to={`/exhibitions/${exhibition.id}`}
        style={{
          padding: "32px 0",
          borderTop: "1px solid var(--color-border)",
        }}
      >
        <p
          style={{
            color: "var(--color-muted)",
            fontSize: "11px",
          }}
        >
          {String(index + 1).padStart(2, "0")}
        </p>

        <h2
          style={{
            marginTop: "12px",
            fontSize: "clamp(36px, 5vw, 64px)",
          }}
        >
          {exhibition.title}
        </h2>

        <p
          style={{
            color: "var(--color-accent)",
            fontSize: "12px",
          }}
        >
          {exhibition.startDate} — {exhibition.endDate}
        </p>

        {exhibition.description && (
          <p
            style={{
              maxWidth: "600px",
              color: "var(--color-muted)",
            }}
          >
            {exhibition.description}
          </p>
        )}
      </Link>
    ))
  )}
</section>

      <Link
        to="/"
        style={{
          display: "inline-block",
          marginTop: "40px",
          color: "var(--color-accent)",
        }}
      >
        ← BACK TO EXPLORE
      </Link>
    </main>
  );
}