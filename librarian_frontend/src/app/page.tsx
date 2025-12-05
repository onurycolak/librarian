import Link from "next/link";

export default function HomePage() {
  return (
    <main className="min-h-screen bg-black text-white flex items-center justify-center px-4">
      <div className="w-full max-w-5xl">
        {/* Header */}
        <div className="text-center mb-12">
          <h1 className="text-3xl md:text-4xl font-semibold mb-2">
            Welcome to Librarian
          </h1>
          <p className="text-sm md:text-base text-gray-400">
            Choose how you want to work: organize your Library or talk to the Librarian.
          </p>
        </div>

        {/* Split cards */}
        <div className="grid grid-cols-1 md:grid-cols-[1fr_minmax(0,0.05fr)_1fr] gap-6 items-stretch">
          {/* Library card */}
          <Link
            href="/library"
            className="group border border-gray-700 rounded-2xl p-6 flex flex-col justify-between hover:border-cyan-400 hover:bg-gradient-to-br hover:from-gray-900 hover:to-cyan-900/30 transition"
          >
            <div className="flex items-center gap-4 mb-4">
              {/* Icon placeholder */}
              <div className="h-12 w-12 rounded-xl bg-cyan-500/20 border border-cyan-500/60 flex items-center justify-center text-xs text-cyan-300">
                Library
              </div>
              <div>
                <h2 className="text-xl font-semibold">Library</h2>
                <p className="text-xs text-gray-400">
                  Structured documentation space.
                </p>
              </div>
            </div>

            <p className="text-sm text-gray-300 mb-4">
              Create, organize, and maintain your documentation. The Library is
              the source of truth your AI works on.
            </p>

            <span className="text-sm font-medium text-cyan-300 group-hover:underline">
              Enter Library →
            </span>
          </Link>

          {/* Separator */}
          <div className="hidden md:flex items-center justify-center">
            <div className="h-24 w-px bg-gray-700" />
          </div>

          {/* Librarian card */}
          <Link
            href="/librarian"
            className="group border border-gray-700 rounded-2xl p-6 flex flex-col justify-between hover:border-purple-400 hover:bg-gradient-to-br hover:from-gray-900 hover:to-purple-900/30 transition"
          >
            <div className="flex items-center gap-4 mb-4">
              {/* Icon placeholder */}
              <div className="h-12 w-12 rounded-xl bg-purple-500/20 border border-purple-500/60 flex items-center justify-center text-xs text-purple-300">
                AI
              </div>
              <div>
                <h2 className="text-xl font-semibold">Librarian</h2>
                <p className="text-xs text-gray-400">
                  RAG / AI assistant for your docs.
                </p>
              </div>
            </div>

            <p className="text-sm text-gray-300 mb-4">
              Ask questions, generate new content, and refactor existing docs.
              The Librarian is powered by your Library.
            </p>

            <span className="text-sm font-medium text-purple-300 group-hover:underline">
              Talk to Librarian →
            </span>
          </Link>
        </div>
      </div>
    </main>
  );
}
