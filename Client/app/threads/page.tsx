"use client";

import { useState } from "react";

import ChatSection from "@/components/threads/chat-section";
import PDFSection from "@/components/threads/pdf-section";
import ThreadSidebar from "@/components/threads/thread-sidebar";
import ThreadProvider from "@/components/threads/threads-context-provider";

export default function ThreadsPage() {
  const [thread, setThread] = useState<number>(0);

  return (
    <main className="flex gap-2">
      <ThreadProvider>
        <ThreadSidebar thread={thread} setThread={setThread} />
        <PDFSection />
        <ChatSection threadId={thread} />
      </ThreadProvider>
    </main>
  );
}
