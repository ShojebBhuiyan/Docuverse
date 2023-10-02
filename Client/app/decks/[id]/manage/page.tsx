"use client"

import React, { useEffect, useState } from "react"
import * as z from "zod"


import { CardProps } from "@/types/types"
import { Button } from "@/components/ui/button"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import DeleteFlashcard from "@/components/DeleteFlashcard"


const page = () => {
  const [data, setData] = useState<CardProps[]>()
  const [reloadKey, setReloadKey] = useState(0)

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/flashcard/all")
      .then((res) => res.json())
      .then((data) => {
        setData(data)
        console.log(data)
      })
  }, [reloadKey])

  const deleteUser = async (id: number) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/flashcard/delete`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            id,
          }),
        }
      )

      if (response.status === 200) {
        const responseData = await response.json()
        setReloadKey(reloadKey + 1)
      } else {
        console.error("Delete request failed")
      }
    } catch (error) {
      console.error("An error occurred", error)
    }
  }

  const handleDelete = (e: React.MouseEvent<HTMLButtonElement>, id: number) => {
    deleteUser(id)
  }

  return (
    <div className="flex flex-wrap flex-col w-[1000px] ml-[325px] mt-[100px] gap-9">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="w-[100px]">ID</TableHead>
            <TableHead>Title</TableHead>
            <TableHead>Front</TableHead>
            <TableHead>Back</TableHead>
            <TableHead></TableHead>
          </TableRow>
        </TableHeader>
        {data?.map((post) => (
          <TableBody>
            <TableRow>
              <TableCell className="font-medium">{post.id}</TableCell>
              <TableCell>{post.title}</TableCell>
              <TableCell>{post.question}</TableCell>
              <TableCell>{post.answer}</TableCell>
              <TableCell className="flex gap-2">
              
                <Button size="sm">Edit</Button>
                <DeleteFlashcard handleDelete={handleDelete} id={post.id}  />
              </TableCell>
            </TableRow>
          </TableBody>
        ))}
      </Table>
    </div>

  )
}

export default page
