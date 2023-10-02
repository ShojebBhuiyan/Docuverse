"use client";

import { useState } from "react";

import { Avatar, AvatarFallback } from "../ui/avatar";
import { Textarea } from "../ui/textarea";
import { useThread } from "./threads-context-provider";

export default function ChatSection() {
  const [messages, setMessages] = useState<
    | {
        content: string;
        role: "system" | "user" | "assistant" | "error";
      }[]
    | null
  >([
    {
      content: "Hello there!",
      role: "system",
    },
  ]);

  const context = useThread();

  const [input, setInput] = useState("");
  return (
    <section className="flex h-screen w-4/5 flex-col overflow-y-auto ">
      <div className="border-primary flex h-[90%] flex-col-reverse rounded-lg border-2">
        <ul className="flex flex-col">
          {messages
            ?.filter((message) => message.role !== "system")
            .map((message, index) => (
              <li className="py-4" key={index}>
                {message.role === "user" ? (
                  <div className="md:text-md flex flex-col items-end gap-2 px-2 text-lg">
                    <Avatar>
                      <AvatarFallback className="bg-[#0DA1CF14]">
                        U
                      </AvatarFallback>
                    </Avatar>
                    <div className="h-fit w-fit rounded-md p-2">
                      <span className="whitespace-pre-line">
                        {message.content}
                      </span>
                    </div>
                  </div>
                ) : (
                  <div className="md:text-md flex flex-col gap-2 self-end p-2 text-lg">
                    <Avatar>
                      <AvatarFallback className="bg-primary">S</AvatarFallback>
                    </Avatar>
                    <div className="bg-primary h-fit w-fit rounded-md p-2">
                      <span className="whitespace-pre-line">
                        {message.content}
                      </span>
                    </div>
                  </div>
                )}
              </li>
            ))}
        </ul>
      </div>
      <Textarea
        placeholder="Say hello!"
        onChange={(e) => setInput(e.target.value)}
        value={input}
        className="border-primary h-full border-2 text-lg"
        onKeyDown={async (e) => {
          if (e.key === "Enter" && !e.shiftKey) {
            const response = await fetch(
              "http://localhost:8080/api/v1/chatbot/chat",
              {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                },
                body: JSON.stringify({
                  threadId: context?.threadId,
                  message: input,
                }),
              }
            );

            const data = await response.json();
            setMessages((prevData) => {
              if (prevData) {
                return [...prevData, data];
              } else {
                return [data];
              }
            });
          }
        }}
      />
    </section>
  );
}
