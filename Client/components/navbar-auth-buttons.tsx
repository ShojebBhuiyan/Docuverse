"use client";

import { useEffect, useState } from "react";

import NavButton from "./nav-button";
import { Button } from "./ui/button";

export default function NavbarAuthButtons() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

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
