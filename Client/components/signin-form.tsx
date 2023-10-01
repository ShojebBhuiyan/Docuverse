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

const signinFormSchema = z.object({
  email: z.string().email(),
  password: z.string(),
});

export default function SignInForm() {
  const form = useForm<z.infer<typeof signinFormSchema>>({
    resolver: zodResolver(signinFormSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const { toast } = useToast();
  const router = useRouter();

  async function onSubmit(values: z.infer<typeof signinFormSchema>) {
    await fetch("http://localhost:8080/api/v1/auth/signin", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: values.email,
        password: values.password,
      }),
    }).then(async (res) => {
      if (res.status === 200) {
        toast({
          variant: "default",
          description: "Login successful!",
        });
        const data = await res.json();
        setAuthToken(data.token);
        router.replace("/dashboard");
      } else if (res.status === 400) {
        toast({
          variant: "destructive",
          description: "Invalid credentials",
        });
      } else if (res.status === 404) {
        toast({
          variant: "warning",
          description: "User not found!",
        });
      } else {
        toast({
          variant: "destructive",
          description: "Something went wrong",
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
          <h1 className="text-primary text-4xl font-bold">Sign In</h1>
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
                  className="border-primary border"
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
                  className="border-primary border"
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
          <h3 className="text-lg">Don&apos;t have an account?</h3>
          <Link href={"/signup"} className="text-lg underline">
            Sign Up
          </Link>
        </div>
      </form>
    </Form>
  );
}
