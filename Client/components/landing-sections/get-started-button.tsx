"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

import { getAuthToken } from "@/config/tokens";

import { Button } from "../ui/button";

export default function GetStartedButton() {
  const router = useRouter();
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    if (getAuthToken() !== null) setIsAuthenticated(true);
  }, []);
  return (
    <Button
      className="my-5 w-3/5"
      onClick={() =>
        isAuthenticated ? router.push("/dashboard") : router.push("/signup")
      }
    >
      Get started
    </Button>
  );
}
