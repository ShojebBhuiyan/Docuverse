"use client";

import { useState } from "react";
import { Pen } from "lucide-react";

import { Button } from "../ui/button";
import { Input } from "../ui/input";
import { useThread } from "./threads-context-provider";

interface ThreadButtonProps {
  threadTitle: string;
  threadId: number;
  setThreads: React.Dispatch<
    React.SetStateAction<{ title: string; id: number }[] | null | undefined>
  >;
}

export default function ThreadButtton({
  threadTitle,
  threadId,
  setThreads,
}: ThreadButtonProps) {
  const [isEditing, setIsEditing] = useState(false);
  const [newName, setNewName] = useState("");

  const context = useThread();

  return (
    <div className="flex items-center justify-center gap-1">
      {isEditing ? (
        <Input
          type="text"
          className="border-0"
          value={newName.length === 0 ? threadTitle : newName}
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
                    id: threadId,
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
          onClick={() => {
            context?.setThreadId(threadId);
          }}
        >
          {threadTitle}
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
  );
}
