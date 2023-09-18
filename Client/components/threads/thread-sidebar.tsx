import { Button } from "../ui/button"

interface ThreadSidebarProps {
  title: string
}

export default function ThreadSidebar({ title }: ThreadSidebarProps) {
  return (
    <div className="flex flex-col gap-2 bg-secondary">
      <Button variant={"secondary"} className="rounded-0">
        {title}
      </Button>
    </div>
  )
}
