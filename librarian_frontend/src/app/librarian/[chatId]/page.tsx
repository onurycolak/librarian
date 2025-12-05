import { LibrarianClient } from '../LibrarianClient';

type PageProps = {
  params: Promise<{
    chatId: string;
  }>;
};

export default async function LibrarianChatPage({ params }: PageProps) {
  const { chatId } = await params;

  return <LibrarianClient activeChatId={chatId} />;
}
