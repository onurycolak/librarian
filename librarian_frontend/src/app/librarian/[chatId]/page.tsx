import { LibrarianClient } from '@/app/librarian/LibrarianClient';

type LibrarianChatPageProps = {
  params: {
    chatId: string;
  };
};

export default function LibrarianChatPage({ params }: LibrarianChatPageProps) {
  const { chatId } = params;

  return <LibrarianClient activeChatId={chatId} />;
}
