"use client"

import { useEffect, useState } from "react"
import { useParams } from "next/navigation"
import { ChevronLeft, ChevronRight } from "lucide-react"

import { CardProps } from "@/types/types"
import { Button } from "@/components/ui/button"

const page = () => {
  const { id } = useParams()
  const [count, setCount] = useState(0)
  const [data, setData] = useState<CardProps[]>()
  const [front, setFront] = useState(true)

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/flashcard/all")
      .then((res) => res.json())
      .then((data) => {
        setData(data)
        console.log(data)
      })
  }, [])

  const handleClickLeft = (e: React.MouseEvent<HTMLButtonElement>) => {
    //e.preventDefault();
    count > 0 && setCount(count - 1)
  }

  const handleClickRight = (e: React.MouseEvent<HTMLButtonElement>) => {
    //e.preventDefault();
    setFront(true)
    setCount(count + 1)
  }

  const handleCard = (e: React.MouseEvent<HTMLButtonElement>) => {
    //e.preventDefault();
    setFront(!front)
  }

  return (
    <div className="flex mx-16 my-16">
      <div className="flex flex-col  w-[1000px] h-[400px] items-end [perspective:1000px] gap-2  ">
        <div
          className={`relative border h-full w-full rounded-xl shadow-xl transition-all duration-500 [transform-style:preserve-3d] ${
            front ? "" : "[transform:rotateY(180deg)]"
          }`}
        >
          {front ? (
            <div className="flex items-center justify-center absolute inset-0 rounded-xl px-12  [backface-visibility:hidden] ">
              <div className="">
              {data?.[count]?.question}
              </div>
            </div>
          ) : (
            <div className="absolute flex items-center justify-center inset-0 rounded-xl px-12 [transform:rotateY(180deg)] [backface-visibility:hidden]">
              <div>
              {data?.[count]?.answer}
              </div>
            </div>
          )}
        </div>
        {/* <h1>
          Deck {id} : {count}
        </h1> */}
        {/* <h1 className="">{front ? "Question" : "Answer"}</h1> */}
        {/* <div className="flex w-full h-[350px] items-center ">
          This is the front of the card and nothing can be done in this era plus this will be in your job to do that
          {front ? data?.[count]?.question : data?.[count]?.answer}
          </div> */}
        <Button className="" onClick={handleCard} variant="default" size="lg">
          Flip Card
        </Button>
      </div>
      <div className="flex w-[800px] p-4 justify-center gap-4 ">
        <Button
          className=""
          onClick={handleClickLeft}
          variant="outline"
          size="icon"
        >
          <ChevronLeft className="h-4 w-4" />
        </Button>
        <Button
          className=""
          onClick={handleClickRight}
          variant="outline"
          size="icon"
        >
          <ChevronRight className="h-4 w-4" />
        </Button>
      </div>
    </div>
  )
}

export default page
