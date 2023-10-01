"use client";

import { useEffect, useState } from "react";

import { Button } from "../ui/button";
import { Skeleton } from "../ui/skeleton";

export default function ThreadSidebar() {
  const [threads, setThreads] = useState<
    { title: string; id: string }[] | null
  >();
  const [isLoading, setIsLoading] = useState(true);

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
      setIsLoading(false);
    };
    fetchThreads();
  }, []);
  return (
    <div className="bg-secondary flex flex-col gap-2">
      {isLoading ? (
        <Skeleton />
      ) : (
        threads?.map((thread) => (
          <Button variant={"secondary"} className="rounded-0">
            {thread.title}
          </Button>
        ))
      )}
    </div>
  );
}
