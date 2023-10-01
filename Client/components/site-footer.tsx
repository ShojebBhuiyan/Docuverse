import Link from "next/link";
import { Facebook, Linkedin, Twitter } from "lucide-react";

import { siteConfig } from "@/config/site";

import { Button } from "./ui/button";
import Divider from "./ui/divider";

export function SiteFooter() {
  return (
    <footer className="p-10">
      <div className="grid gap-10 space-x-1 sm:grid-cols-1 sm:items-start sm:justify-center md:grid-cols-3 md:items-center md:justify-center">
        <div className="flex justify-center">
          <h1 className="text-4xl font-semibold">{siteConfig.name}</h1>
        </div>
        <div className="flex flex-col justify-center gap-2">
          <h3 className="text-lg font-semibold">Quick Links</h3>
          <Link href={"/features"}>Features</Link>
        </div>
        <div className="flex flex-col items-center gap-2">
          <h2 className="text-xl">Connect with us</h2>
          <div className="flex justify-around gap-2">
            <Button className="border-primary rounded-full border bg-inherit">
              <Linkedin className="text-primary" />
            </Button>
            <Button className="border-primary rounded-full border bg-inherit">
              <Facebook className="text-primary" />
            </Button>
            <Button className="border-primary rounded-full border bg-inherit">
              <Twitter className="text-primary" />
            </Button>
          </div>
        </div>
      </div>
      <Divider />
      <div className="container flex flex-col items-center justify-center gap-5 pt-4 md:flex-row md:gap-0">
        <div className="flex flex-col md:flex-row md:gap-1">
          <h3 className="text-center text-lg">© 2023 Docuverse.</h3>
          <h3 className="text-center text-lg">All Rights Reserved.</h3>
        </div>
      </div>
    </footer>
  );
}
