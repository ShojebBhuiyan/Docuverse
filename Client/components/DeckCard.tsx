import { PostProps } from "@/types/types";
import React from "react";
import { Card,CardTitle } from "@/components/ui/card";
import Link from "next/link";

const DeckCard = ({title, id}: PostProps) => {
  return (
    <Card className="flex flex-col border-collapse items-center justify-center h-[250px] w-[300px] gap-4 rounded-xl shadow-xl ">
       <CardTitle>
        <Link href={`/decks/${id}`}> Sample Card Title </Link>
       </CardTitle>
      <div className="text-lg">37 cards</div>
    </Card>
  );
};

export default DeckCard;
