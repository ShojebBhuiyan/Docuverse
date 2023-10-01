"use client"
import { Card,CardTitle } from "@/components/ui/card"
import Link  from 'next/link'
import {useParams} from 'next/navigation'


export default function Page() {

    const { id } = useParams()
  
  return (
    <div className="flex py-40 mx-60 my-14 items-center justify-center gap-8 border ">
       <Card className="w-48 h-12 flex items-center justify-center ">
        <CardTitle>
       <Link href={`/decks/${id}/manage`}>Manage</Link>
        </CardTitle>
       </Card>
       <Card className="w-48 h-12 flex items-center justify-center ">
       <CardTitle>
        <Link href={`/decks/${id}/study`}>Study</Link>
       </CardTitle>
       </Card>
    </div>
  )
}
