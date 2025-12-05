'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import styles from './Librarian.module.css';

type RecentChat = {
  id: string;
  title: string;
  updatedAt: string;
};

type ChatMessage = {
  content: string;
  role: string;
};

type ChatResponse = {
  id: string;
  title: string;
  messages: ChatMessage[];
  updatedAt: string;
};

type Props = {
  activeChatId?: string;
};

const BACKEND_BASE_URL = 'http://localhost:8080';

export function LibrarianClient({ activeChatId }: Props) {
  const router = useRouter();
  const [recents, setRecents] = useState<RecentChat[]>([]);
  const [loadingRecents, setLoadingRecents] = useState(true);
  const [recentsError, setRecentsError] = useState<string | null>(null);

  const [pendingMessage, setPendingMessage] = useState('');
  const [isSending, setIsSending] = useState(false);
  const [messages, setMessages] = useState<ChatMessage[]>([]);

  useEffect(() => {
    const fetchRecents = async () => {
      try {
        setLoadingRecents(true);
        setRecentsError(null);

        const res = await fetch(
          `${BACKEND_BASE_URL}/api/librarian/chat/recents`,
        );
        if (!res.ok) {
          throw new Error(`Recents returned ${res.status}`);
        }

        const data = (await res.json()) as RecentChat[];
        setRecents(data);
      } catch (err) {
        console.error(err);
        setRecentsError('Could not load recent chats');
      } finally {
        setLoadingRecents(false);
      }
    };

    fetchRecents();
  }, []);

  const handleNewChat = () => {
    // On /librarian (no active chat): no-op
    if (!activeChatId) return;

    // On /librarian/:id -> go back to fresh state
    setMessages([]);
    setPendingMessage('');
    router.push('/librarian');
  };

  const handleSelectChat = (id: string) => {
    // For now we don’t reload messages; just clear and route
    setMessages([]);
    setPendingMessage('');
    router.push(`/librarian/${id}`);
  };

  const handleSend = async () => {
    const text = pendingMessage.trim();
    if (!text || isSending) return;

    setIsSending(true);
    try {
      if (!activeChatId) {
        // NEW CHAT FLOW: send first message, get id & messages, route there
        const res = await fetch(`${BACKEND_BASE_URL}/api/librarian/chat`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            firstMessage: {
              content: text,
              role: 'user',
            },
          }),
        });

        if (!res.ok) {
          throw new Error(`New chat returned ${res.status}`);
        }

        const data = (await res.json()) as ChatResponse;
        if (!data.id) {
          throw new Error('Backend did not return an id');
        }

        setMessages(data.messages ?? []);
        setPendingMessage('');
        router.push(`/librarian/${data.id}`);
      } else {
        // EXISTING CHAT FLOW (not wired to backend yet)
        console.log('Would send message to chat', activeChatId, text);
        setMessages((prev) => [...prev, { content: text, role: 'user' }]);
        setPendingMessage('');
      }
    } catch (err) {
      console.error(err);
    } finally {
      setIsSending(false);
    }
  };

  const renderSidebarContent = () => {
    if (loadingRecents) {
      return <div className={styles.sidebarEmpty}>Loading recent chats…</div>;
    }

    if (recentsError) {
      return <div className={styles.sidebarEmpty}>{recentsError}</div>;
    }

    if (!recents.length) {
      return (
        <div className={styles.sidebarEmpty}>
          No recent chats yet. Start a new conversation with the Librarian.
        </div>
      );
    }

    return (
      <div className={styles.recentsList}>
        {recents.map((chat) => {
          const isActive = chat.id === activeChatId;
          const itemClass = [
            styles.recentItem,
            isActive ? styles.recentItemActive : '',
          ]
            .filter(Boolean)
            .join(' ');

          return (
            <button
              key={chat.id}
              type="button"
              className={itemClass}
              onClick={() => handleSelectChat(chat.id)}
            >
              <div className={styles.recentTitle}>{chat.title}</div>
              <div className={styles.recentDate}>
                {formatUpdateDate(chat.updatedAt)}
              </div>
            </button>
          );
        })}
      </div>
    );
  };

  const hasActiveChat = Boolean(activeChatId);

  const renderMessages = () => {
    if (!messages.length) {
      return (
        <div className={styles.messages}>
          {hasActiveChat
            ? 'Messages will appear here once we wire the LLM backend.'
            : 'Your first message will start a new chat with the Librarian.'}
        </div>
      );
    }

    return (
      <div className={styles.messages}>
        <div style={{ width: '100%' }}>
          {messages.map((m, idx) => (
            <div
              key={idx}
              style={{
                marginBottom: 8,
                textAlign: m.role === 'user' ? 'right' : 'left',
                opacity: 0.9,
              }}
            >
              <div style={{ fontSize: 11, marginBottom: 2 }}>
                {m.role === 'user' ? 'You' : 'Assistant'}
              </div>
              <div>{m.content}</div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  return (
    <div className={styles.page}>
      <header className={styles.navbar}>
        <div className={styles.navTitle}>Librarian · Chat</div>
        <div className={styles.navRight}>RAG &amp; documentation assistant</div>
      </header>

      <div className={styles.content}>
        {/* Sidebar */}
        <aside className={styles.sidebar}>
          <div className={styles.sidebarHeader}>
            <div className={styles.sidebarTitle}>Recent chats</div>
            <button
              type="button"
              className={styles.newChatButton}
              onClick={handleNewChat}
            >
              New chat
            </button>
          </div>

          {renderSidebarContent()}
        </aside>

        {/* Main chat area */}
        <section className={styles.chatArea}>
          <div className={styles.chatHeader}>
            <div className={styles.chatTitle}>
              {hasActiveChat ? 'Chat session' : 'New chat'}
            </div>
            <div className={styles.chatSubtitle}>
              {hasActiveChat
                ? `Chat ID: ${activeChatId}`
                : 'Send a message to start a new conversation, or pick one from the left.'}
            </div>
          </div>

          {renderMessages()}

          <div className={styles.inputBar}>
            <input
              className={styles.inputField}
              placeholder="Type a message to the Librarian…"
              value={pendingMessage}
              onChange={(e) => setPendingMessage(e.target.value)}
            />
            <button
              type="button"
              className={styles.sendButton}
              disabled={!pendingMessage.trim() || isSending}
              onClick={handleSend}
            >
              Send
            </button>
          </div>
        </section>
      </div>
    </div>
  );
}

function formatUpdateDate(iso: string) {
  if (!iso) return '';
  try {
    const d = new Date(iso);
    if (Number.isNaN(d.getTime())) return '';
    return d.toLocaleString();
  } catch {
    return '';
  }
}
