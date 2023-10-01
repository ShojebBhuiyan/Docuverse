"use client";

import { useRouter } from "next/navigation";

import { Button } from "../ui/button";

export default function GetStartedButton() {
  const router = useRouter();
  return (
    <Button className="my-5 w-3/5" onClick={() => router.push("/signup")}>
      Get started
    </Button>
  );
}
