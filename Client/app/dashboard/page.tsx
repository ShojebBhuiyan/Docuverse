import DashboardButton from "@/components/dashboard-button";

export default function DashboardPage() {
  return (
    <section>
      <div className="container flex h-[80vh] items-center justify-around">
        <DashboardButton href="/threads" pageName="Go To Threads" />
        <DashboardButton href="/decks" pageName="Go To Decks" />
      </div>
    </section>
  );
}
