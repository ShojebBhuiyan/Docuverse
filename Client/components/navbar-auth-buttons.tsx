"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

import NavButton from "./nav-button";
import { Button } from "./ui/button";
import { useToast } from "./ui/use-toast";

export default function NavbarAuthButtons() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const router = useRouter();
  const { toast } = useToast();

  useEffect(() => {
    const token = localStorage.getItem("auth-token");
    if (token) {
      setIsAuthenticated(true);
    }
  }, []);
  return (
    <>
      {isAuthenticated ? (
        <>
          <NavButton description="Profile" href="/profile" variant="default" />
          <Button
            variant={"default"}
            className="text-lg"
            onClick={() => {
              localStorage.removeItem("auth-token");
              setIsAuthenticated(false);
              router.push("/");
              toast({
                variant: "default",
                description: "Signed out successfully!",
              });
            }}
          >
            Signout
          </Button>
        </>
      ) : (
        <>
          <NavButton description="Sign In" href="/signin" variant="outline" />
          <NavButton description="Sign Up" href="/signup" variant="default" />
        </>
      )}
    </>
  );
}
