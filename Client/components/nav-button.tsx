"use client";

import { useRouter } from "next/navigation";

import { Button } from "./ui/button";

interface NavButtonProps {
  href: string;
  description: string;
  variant: "default" | "outline" | "destructive" | "link" | "ghost";
}

export default function NavButton({
  href,
  description,
  variant,
}: NavButtonProps) {
  const router = useRouter();

  return (
    <Button
      variant={variant}
      className="text-lg"
      onClick={() => {
        router.push(href);
      }}
    >
      {description}
    </Button>
  );
}
