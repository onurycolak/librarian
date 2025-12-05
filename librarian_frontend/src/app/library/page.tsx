'use client';

import { useEffect, useState } from 'react';

export default function PingTestPage() {
  const [message, setMessage] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchPing = async () => {
      try {
        const res = await fetch('http://localhost:8080/library');
        if (!res.ok) {
          throw new Error(`Backend returned ${res.status}`);
        }

        const text = await res.text();
        setMessage(text);
      } catch (err) {
        console.error(err);
        setError('Failed to reach backend');
      } finally {
        setLoading(false);
      }
    };

    fetchPing();
  }, []);

  return (
    <main className="min-h-screen flex flex-col items-center justify-center p-8">
      <h1 className="text-2xl font-semibold mb-4">
        Library Test
      </h1>

      {loading && <p>Contacting backend…</p>}

      {!loading && error && (
        <p className="text-red-600">
          {error}
        </p>
      )}

      {!loading && !error && (
        <p className="text-lg">
          {message}
        </p>
      )}
    </main>
  );
}
