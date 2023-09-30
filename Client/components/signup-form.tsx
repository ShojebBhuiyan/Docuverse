"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { setAuthToken } from "@/config/tokens";

import { Button } from "./ui/button";
import { Form, FormControl, FormField, FormItem, FormMessage } from "./ui/form";
import { Input } from "./ui/input";
import { useToast } from "./ui/use-toast";

const signupFormSchema = z
  .object({
    name: z.string().min(1, "Name can't be empty!"),
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
  });

export default function SignUpForm() {
  const form = useForm<z.infer<typeof signupFormSchema>>({
    resolver: zodResolver(signupFormSchema),
    defaultValues: {
      name: "",
      email: "",
      password: "",
    },
  });

  const { toast } = useToast();
  const router = useRouter();

  async function onSubmit(values: z.infer<typeof signupFormSchema>) {
    await fetch("http://localhost:8080/api/v1/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: values.name,
        email: values.email,
        password: values.password,
      }),
    }).then(async (res) => {
      if (res.status === 201) {
        toast({
          description: "You have successfully signed up!",
        });
        const data = await res.json();
        setAuthToken(data.token);
        router.replace("/dashboard");
      } else if (res.status === 400) {
        toast({
          variant: "warning",
          description: "This email is already in use.",
        });
      } else {
        toast({
          variant: "destructive",
          title: "Uh oh!",
          description: "Something went wrong. Please try again.",
        });
      }
    });
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
        <FormField
          control={form.control}
          name="name"
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
  );
}
