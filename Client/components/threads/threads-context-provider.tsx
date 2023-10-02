import { createContext, useContext, useState } from "react";

interface ThreadContextType {
  threadId: number | null;
  setThreadId: (threadId: number | null) => void;
}

const ThreadContext = createContext<ThreadContextType | undefined>(undefined);

export default function ThreadProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [threadId, setThreadId] = useState<number | null>(0);

  const context: ThreadContextType = {
    threadId,
    setThreadId,
  };

  return (
    <ThreadContext.Provider value={context}>{children}</ThreadContext.Provider>
  );
}

export function useThread() {
  const context = useContext(ThreadContext);

  return context;
}
