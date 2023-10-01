import * as React from "react";
import Link from "next/link";

import { NavItem } from "@/types/nav";
import { siteConfig } from "@/config/site";
import { cn } from "@/lib/utils";

import NavbarAuthButtons from "./navbar-auth-buttons";
import { ThemeToggle } from "./theme-toggle";

interface MainNavProps {
  items?: NavItem[];
}

export function MainNav({ items }: MainNavProps) {
  return (
    <div className="flex w-full items-center justify-between md:gap-10">
      <div className="flex sm:justify-center">
        <Link href="/" className="flex items-center space-x-2">
          <span className="inline-block text-3xl font-extrabold">
            {siteConfig.name}
          </span>
        </Link>
      </div>

      {items?.length ? (
        <>
          <nav className="hidden w-full gap-6 md:flex md:justify-end">
            {items?.map(
              (item, index) =>
                item.href && (
                  <Link
                    key={index}
                    href={item.href}
                    className={cn(
                      "text-primary flex items-center text-lg font-medium",
                      item.disabled && "cursor-not-allowed opacity-80"
                    )}
                  >
                    {item.title}
                  </Link>
                )
            )}
          </nav>
        </>
      ) : null}
      <div className="flex justify-end gap-2">
        <NavbarAuthButtons />
        <ThemeToggle />
      </div>
    </div>
  );
}
