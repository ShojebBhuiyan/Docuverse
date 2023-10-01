import ChatSection from "@/components/threads/chat-section";
import PDFSection from "@/components/threads/pdf-section";
import ThreadSidebar from "@/components/threads/thread-sidebar";

export default async function ThreadsPage() {
  return (
    <main className="flex gap-2">
      <ThreadSidebar />
      <PDFSection />
      <ChatSection />
    </main>
  );
}
