"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { Pen } from "lucide-react";

import { Button } from "../ui/button";
import { Input } from "../ui/input";
import { Skeleton } from "../ui/skeleton";

export default function ThreadSidebar() {
  const [threads, setThreads] = useState<
    { title: string; id: number }[] | null
  >();
  const [isActive, setIsActive] = useState<boolean[]>();
  const [isLoading, setIsLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [newName, setNewName] = useState("");

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
          <div className="flex items-center justify-center gap-1">
            {isEditing ? (
              <Input
                type="text"
                className="border-0"
                value={newName.length === 0 ? thread.title : newName}
                onChange={(e) => setNewName(e.target.value)}
                onKeyDown={async (e) => {
                  if (e.key === "Enter" && !e.shiftKey) {
                    const res = await fetch(
                      "http://localhost:8080/api/v1/thread/update-title",
                      {
                        method: "POST",
                        headers: {
                          "Content-Type": "application/json",
                          Authorization:
                            "Bearer " + localStorage.getItem("auth-token"),
                        },
                        body: JSON.stringify({
                          id: thread.id,
                          title: newName,
                        }),
                      }
                    );

                    const data = await res.json();
                    setThreads(data);
                    setIsEditing(false);
                    setNewName("");
                  }
                }}
              />
            ) : (
              <Button
                variant={"secondary"}
                className="rounded-0 hover:text-primary"
              >
                {thread.title}
              </Button>
            )}

            <Button
              variant={"ghost"}
              className="h-fit w-fit"
              onClick={() => setIsEditing(true)}
            >
              <Pen size={16} className="hover:text-primary" />
            </Button>
            {/* <div className="border-primary container mt-10 border-t"></div> */}
          </div>
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
