import PDFSection from "@/components/threads/pdf-section";
import ThreadSidebar from "@/components/threads/thread-sidebar";

export default async function ThreadsPage() {
  return (
    <main className="flex gap-0">
      <ThreadSidebar />
      <section className="flex w-full items-center justify-center">
        <PDFSection />
      </section>
    </main>
  );
}
