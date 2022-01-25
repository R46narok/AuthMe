﻿using System.Text;

namespace AuthMe.Infrastructure.Services.ComputerVision;

public static class StringExtensions
{
    /// <summary>
    /// Levensthein
    /// </summary>
    /// <param name="s"></param>
    /// <param name="t"></param>
    /// <returns></returns>
    public static int Distance(this string s, string t)
    {
        int n = s.Length;
        int m = t.Length;
        int[,] d = new int[n + 1, m + 1];
        
        if (n == 0)
        {
            return m;
        }

        if (m == 0)
        {
            return n;
        }
        
        for (int i = 0; i <= n; d[i, 0] = i++)
        {
        }

        for (int j = 0; j <= m; d[0, j] = j++)
        {
        }
        
        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                int cost = (t[j - 1] == s[i - 1]) ? 0 : 1;
                
                d[i, j] = Math.Min(
                    Math.Min(d[i - 1, j] + 1, d[i, j - 1] + 1),
                    d[i - 1, j - 1] + cost);
            }
        }

        return d[n, m];
    }

    public static string ToTitleCase(this string str)
    {
        var builder = new StringBuilder(str.ToLower());
        builder[0] = Char.ToUpper(builder[0]);
        return builder.ToString();
    }
}