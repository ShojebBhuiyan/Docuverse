"use client"

import Link from "next/link"
import { useRouter } from "next/navigation"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import { z } from "zod"

import { Button } from "./ui/button"
import { Form, FormControl, FormField, FormItem, FormMessage } from "./ui/form"
import { Input } from "./ui/input"
import { useToast } from "./ui/use-toast"

const signupFormSchema = z
  .object({
    firstName: z.string().min(1, "Name can't be empty!"),
    lastName: z.string().min(1, "Name can't be empty!"),
    email: z.string().email(),
    password: z
      .string()
      .min(8, "Password must be at least 8 characters long")
      .regex(
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
        "Password must contain at least one letter, one number, and one special character"
      ),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  })

export default function SignUpForm() {
  const form = useForm<z.infer<typeof signupFormSchema>>({
    resolver: zodResolver(signupFormSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
    },
  })

  const { toast } = useToast()
  const router = useRouter()

  async function onSubmit(values: z.infer<typeof signupFormSchema>) {
    await fetch("/api/search-user", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: values.email,
      }),
    }).then(async (res) => {
      if (res.status === 200) {
        toast({
          variant: "warning",
          description: "This email is already in use.",
        })
      } else {
        await fetch("/api/signup", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            firstName: values.firstName,
            lastName: values.lastName,
            email: values.email,
            password: values.password,
          }),
        }).then((res) => {
          if (res.status === 200) {
            toast({
              description: "You have successfully signed up!",
            })
            router.replace("/guided-form")
          } else {
            toast({
              variant: "destructive",
              title: "Uh oh!",
              description: "Something went wrong. Please try again.",
            })
          }
        })
      }
    })
  }

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex w-[20rem] flex-col space-y-2"
      >
        <div className="mb-5 flex justify-center">
          <h1 className="text-4xl font-bold text-[#15bebe]">Sign Up</h1>
        </div>
        <div className="mt-5 flex gap-2">
          <FormField
            control={form.control}
            name="firstName"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    placeholder="your first name"
                    {...field}
                    className="border border-black"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="lastName"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    placeholder="your last name"
                    {...field}
                    className="border border-black"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input
                  placeholder="youremail@xyz.com"
                  {...field}
                  className="border border-black"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input
                  type="password"
                  placeholder="your password"
                  {...field}
                  className="border border-black"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="confirmPassword"
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input
                  type="password"
                  placeholder="confirm your password"
                  {...field}
                  className="border border-black"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" className="justify-center">
          Submit
        </Button>
        <div className="flex gap-2">
          <h3 className="text-lg">Already have an account?</h3>
          <Link href={"/signin"} className="text-lg underline">
            Sign In
          </Link>
        </div>
      </form>
    </Form>
  )
}
