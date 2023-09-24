"use client"

import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import * as z from "zod"

import { Button } from "@/components/ui/button"
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Textarea } from "@/components/ui/textarea"
import { ChildProps } from "@/types/types"

//define schema
export const FormSchema = z.object({
  front: z
    .string(),
  back: z
    .string(),
})


const AddFlashcardForm = ({reloadKeyState}:ChildProps)=> {
  

  const [reloadKey, setReloadKey] = reloadKeyState
 
  //define form
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
  })

  //refactor
  const addCard = async (data: z.infer<typeof FormSchema>) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/flashcard/add`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            question: data.front,
            answer: data.back,
          }),
        }
      )

      if (response.status === 200) {
        const responseData = await response.json()
        console.log(reloadKey)
        setReloadKey(reloadKey + 1)
      } else {
        console.error("Delete request failed")
      }
    } catch (error) {
      console.error("An error occurred", error)
    }
  }

  //refactor
  function onSubmit(data: z.infer<typeof FormSchema>) {
    console.log(data)
    addCard(data)
  }

  return (
    <div>
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="w-2/3 space-y-6">
        <FormField
          control={form.control}
          name="front"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Front</FormLabel>
              <FormControl>
                <Textarea
                  placeholder="Front side of the card"
                  className="resize-none"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="back"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Back</FormLabel>
              <FormControl>
                <Textarea
                  placeholder="Back side of the card"
                  className="resize-none"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">Submit</Button>
      </form>
    </Form>
    </div>
  )
}

export default AddFlashcardForm