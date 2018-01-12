package com.logicq.ngr.validator;

import javax.annotation.Nullable;

public final class Preconditions {

	public static <T> T checkNotNull(T reference) {
		if (reference == null) {
			throw new NullPointerException();
		}
		return reference;
	}

	public static <T> T checkNotNull(T reference, @Nullable String errorMessage) {
		if (reference == null) {
			throw new NullPointerException(String.valueOf(errorMessage));
		}
		return reference;
	}

	public static <T> T checkNotNull(T reference, @Nullable String errorMessageTemplate,
			@Nullable Object... errorMessageArgs) {

		if (reference == null) {
			throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
		}
		return reference;
	}

	public static void checkArgument(boolean condition) {
		if (!condition) {
			throw new IllegalArgumentException();
		}
	}

	public static void checkArgument(boolean condition, @Nullable Object errorMessage) {
		if (!condition) {
			throw new IllegalArgumentException(String.valueOf(errorMessage));
		}
	}

	private static String format(@Nullable String template, @Nullable Object... args) {
		final int numArgs = args == null ? 0 : args.length;
		template = String.valueOf(template); // null -> "null"

		// start substituting the arguments into the '%s' placeholders
		StringBuilder builder = new StringBuilder(template.length() + 16 * numArgs);
		int templateStart = 0;
		int i = 0;
		while (i < numArgs) {
			int placeholderStart = template.indexOf("%s", templateStart);
			if (placeholderStart == -1) {
				break;
			}
			builder.append(template.substring(templateStart, placeholderStart));
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template.substring(templateStart));

		// if we run out of placeholders, append the extra args in square braces
		if (i < numArgs) {
			builder.append(" [");
			builder.append(args[i++]);
			while (i < numArgs) {
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}

		return builder.toString();
	}

	private Preconditions() {
	}

}
