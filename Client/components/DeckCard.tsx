import { PostProps } from "@/types/types";
import React from "react";
import { Card,CardTitle } from "@/components/ui/card";
import Link from "next/link";

const DeckCard = ({title, id}: PostProps) => {
  return (
    <Card className="flex border items-center justify-center ">
       <CardTitle>
        <Link href={`/decks/${id}`}> {title} </Link>
       </CardTitle>
    </Card>
  );
};

export default DeckCard;
