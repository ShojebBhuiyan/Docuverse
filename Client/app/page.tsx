"use client"
import { Button } from "@/components/ui/button"
import { Card,CardTitle } from "@/components/ui/card"
import Link  from 'next/link'

export default function IndexPage() {
  
  return (
    <div className="flex py-40 mx-60 my-14 items-center justify-center gap-8 border ">
       <Card className="w-48 h-12 flex items-center justify-center ">
       <CardTitle>Ask</CardTitle>
       </Card>
       <Card className="w-48 h-12 flex items-center justify-center ">
       <CardTitle>
        <Link href="/decks"> Flash Card </Link>
       </CardTitle>
       </Card>
    </div>
  )
}
