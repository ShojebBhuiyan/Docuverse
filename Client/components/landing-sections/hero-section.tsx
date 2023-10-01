import { siteConfig } from "@/config/site";

import GetStartedButton from "./get-started-button";

export default function HeroSection() {
  return (
    <section className="flex h-[80vh] items-center justify-center">
      <div className="flex max-w-[30rem] flex-col items-center gap-2">
        <h3 className="flex gap-[0.5rem] tracking-[0.5rem]">
          WELCOME TO{" "}
          <span className="text-primary tracking-[0.5rem]">{` ${siteConfig.name}`}</span>
        </h3>
        <h1 className="text-center text-3xl font-extrabold leading-tight tracking-tighter md:text-4xl">
          {siteConfig.description}
        </h1>
        <div>
          <p className="text-muted-foreground max-w-[700px] text-center text-lg">
            Upload Documents and Generate Flashcards
          </p>
        </div>
        <GetStartedButton />
      </div>
    </section>
  );
}
