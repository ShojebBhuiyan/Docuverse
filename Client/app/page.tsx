"use client"
import { Button } from "@/components/ui/button"
import { Card,CardTitle } from "@/components/ui/card"
import Link  from 'next/link'

export default function IndexPage() {
  
  return (
    <div className="flex py-40 justify-center gap-8">
       <Card className="w-48 h-32 flex items-center justify-center ">
       <CardTitle>Ask</CardTitle>
       </Card>
       <Card className="w-48 h-32 flex items-center justify-center ">
       <CardTitle>
        <Link href="/"> Answer</Link>
       </CardTitle>
       </Card>
    </div>
  )
}
