import PDFSection from "@/components/threads/pdf-section";
import ThreadSidebar from "@/components/threads/thread-sidebar";

export default function ThreadsPage({ params }: { params: { id: string } }) {
  return (
    <main className="flex gap-0">
      <ThreadSidebar title={"true"} />
      <section className="flex w-full items-center justify-center">
        <PDFSection threadId={params.id} />
      </section>
    </main>
  );
}
