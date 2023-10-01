"use client";

import { useRouter } from "next/navigation";

import { Button } from "./ui/button";

interface DashboardButtonProps {
  pageName: string;
  href: string;
}

export default function DashboardButton({
  pageName,
  href,
}: DashboardButtonProps) {
  const router = useRouter();

  return (
    <Button
      className="h-[10rem] w-[20rem] text-3xl"
      onClick={() => router.push(href)}
    >
      {pageName}
    </Button>
  );
}
