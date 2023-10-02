"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { Pen } from "lucide-react";

import { Button } from "../ui/button";
import { Input } from "../ui/input";
import { Skeleton } from "../ui/skeleton";
import ThreadButtton from "./thread-button";
import { useThread } from "./threads-context-provider";

export default function ThreadSidebar() {
  const [threads, setThreads] = useState<
    { title: string; id: number }[] | null
  >();
  const [isActive, setIsActive] = useState<boolean[]>();
  const [isLoading, setIsLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [newName, setNewName] = useState("");

  const context = useThread();

  useEffect(() => {
    const fetchThreads = async () => {
      const res = await fetch("http://localhost:8080/api/v1/thread/", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("auth-token"),
        },
      });
      const data = await res.json();
      setThreads(data);
      setIsActive(data?.map(() => false));
      setIsLoading(false);
    };
    fetchThreads();
  }, []);
  return (
    <div className="bg-secondary flex h-screen w-1/4 flex-col gap-2 overflow-y-auto">
      {isLoading ? (
        <Skeleton className="h-screen" />
      ) : (
        threads?.map((thread) => (
          <ThreadButtton
            threadId={thread.id}
            threadTitle={thread.title}
            setThreads={setThreads}
          />
        ))
      )}
      <div>
        <Button
          variant={"secondary"}
          className="rounded-0"
          onClick={async () => {
            const res = await fetch(
              "http://localhost:8080/api/v1/thread/create",
              {
                method: "POST",
                headers: {
                  Authorization: "Bearer " + localStorage.getItem("auth-token"),
                },
              }
            );
            const data = await res.json();
            setThreads(data);
          }}
        >
          + Create New Thread
        </Button>
      </div>
    </div>
  );
}
