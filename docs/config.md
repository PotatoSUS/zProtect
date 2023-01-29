# Configuration Options

## Introduction

zProtect uses the [YAML](https://en.wikipedia.org/wiki/YAML) data serialization language for configuring the behavior of the zProtect obfuscator. It is possible to hand-write the configuration file to upload on the web interface, however, you may wish to modify the [example file](#example) or generate one using the [command line tool](./commandline.md).

## Value Types
There are a few value types used in the zProtect configuration, which are listed below.

### Boolean
Only accepts `true` and `false` as the value.

For example:
```yaml
# A key with value as true
key1: true

# A key with value as false
key2: false
```

### String
Accepts any text character.

For example:
```yaml
# A key with a string value
key: value
```

### String Array
A list of [`strings`](#string), separated by a newline and prefixed with a dash.

For example:
```yaml
# An array with entries
key:
- something
- another thing

# An empty array
empty: []
```

### Simple Option
A [`string array`](#string-array) with a [`boolean`](#boolean) value `enabled`.

For example:
```yaml
# Option enabled
key:
  enabled: true
```

### Option with Path
A [`string array`](#string-array) with a [`boolean`](#boolean) key `enabled` and a [`string`](#string) key `path`.

For example:
```yaml
# Option enabled
key:
  enabled: true
  path: something
```


## Options
These options specify runtime information and general configuration.

### libraries
Dependencies required to obfuscate your jar to prevent "Not found" errors. 

Value type: [`string array`](#string-array)

### exclusions
Class files and directories that should be ignored by the obfuscator. They will not have any obfuscation applied to them.

Value type: [`string array`](#string-array)

### ForceExclusions
::: tip Note
Transformer priority will fix this in the future.
:::
Class files and directories that should be blacklisted by the obfuscator before processing. This will prevent issues with exclusions if the regular exclusions fail for some unknown reason.

Value type: [`string array`](#string-array)

### Watermark
Adds a watermark to the obfuscated JAR for advertisement. Only removable for users with a Commercial License.

Value type: [`boolean`](#boolean)

## Transformers
These options specify wherether to enable available obfuscation techniques.

### AntiDebug <Badge type="warning" text="incompatibilities" vertical="top" />
::: tip Warning!
This option may cause issues with certain programs.
:::

Blocks debugging options on terminal.

Value type: [`simple option`](#simple-option)

### DecompilerCrasher <Badge type="danger" text="issues" vertical="top" />
::: tip Warning!
This option is very buggy at the moment and will likely cause problems.
You are strongly advised not to use this.
:::

Manipulates instructions to crash decompilers.

Value type: [`simple option`](#simple-option)

### BadAnnotationCrasher
Manipulates annotations to break bad decompilers. This should not cause any major issues.

Value type: [`simple option`](#simple-option)

### Flow
Adds fake jumps, and such to code.

Value type: [`simple option`](#simple-option)

## Renamers
Renames various components of your JAR's contents.

### ClassRenamer
Renames class files.

Value type: [`option with path`](#option-with-path)

### FieldRenamer
Renames field names.

Value type: [`simple option`](#simple-option)

### LocalVariableRenamer
Renames local variables.

Value type: [`simple option`](#simple-option)

### MethodRenamer
Renames methods.

Value type: [`simple option`](#simple-option)

## Optimization
Modifies or removes various aspects of your jar file to make debugging harder.

### EnumOptimiser
Removes all clone call(s) and returns an array to optimize enum values.

Value type: [`simple option`](#simple-option)

### FinalRemover
Removes finals from your code.

Value type: [`simple option`](#simple-option)

### HideClassMembers
Mark classes as synthetic to hide them from bad decompilers.

Value type: [`simple option`](#simple-option)

### InsnRemover
Removes the instructions `const_.` and `tableswitch`.

Value type: [`simple option`](#simple-option)

### KotlinMetadataRemover
Removes Kotlin-specific Metadata such as `NotNull`, `Nullable`, etc.

Value type: [`simple option`](#simple-option)

### NOPInsnRemover
Removes extended type information.

Value type: [`simple option`](#simple-option)

### RemoveSignatures
Removes the signature attribute from classes and methods.

Value type: [`simple option`](#simple-option)

## Poolers
Move values into an array to make the resulting JAR harder.

### NumberPooler
Moves numbers into an array.

Value type: [`simple option`](#simple-option)

### StringPooler
Moves strings into an array.

Value type: [`simple option`](#simple-option)

## Shrinking
Remove various attributes to make it harder to understand the resulting JAR.

### LineNumberRemover
Removes line numbers so StackTraces show `(Unknown)` on errors.

Value type: [`simple option`](#simple-option)

### LocalVariableRemover
Removes the local variable attribute from methods.

Value type: [`simple option`](#simple-option)

### RemoveInnerClasses
Removes various attributes such as `outerClass`, `outerMethod`, etc. from the class.

Value type: [`simple option`](#simple-option)

### SourceDebugRemover
Removes the `SourceDebug` attribute from the class.

Value type: [`simple option`](#simple-option)

### SourceFileRemover
Removes the `SourceFile` attribute from the class.

Value type: [`simple option`](#simple-option)

## Shufflers
Randomly shuffles various elements of your jar file to make it more difficult to find them.

### ShuffleFields
Randomly shuffles elements in the mutable list of fields.

Value type: [`simple option`](#simple-option)

### ShuffleMethods
Randomly shuffles elements in the mutable list of methods.

Value type: [`simple option`](#simple-option)

### ShuffleClasses
Randomly shuffles elements in the mutable list of classes.

Value type: [`simple option`](#simple-option)

## Example
Below is a fully functional example configuration file with some zProtect transformers enabled and some disabled.

```yaml
# zProtect Configuration File Version 1
# Reference: https://docs.zprotect.dev/config.html

# May cause issues with certain programs.
antiDebug:
  enabled: false

# Strongly advised not to use this.
decompilerCrasher:
  enabled: false

badAnnotationCrasher:
  enabled: true

flow:
  enabled: false

classRenamer:
  enabled: false
  path: ""

fieldRenamer:
  enabled: false

localVariableRenamer:
  enabled: false

methodRenamer:
  enabled: false

# Optimization
enumOptimiser:
  enabled: false

finalRemover:
  enabled: false

hideClassMembers:
  enabled: false

insnRemover:
  enabled: false

kotlinMetadataRemover:
  enabled: false

NOPInsnRemover:
  enabled: false

removeSignatures:
  enabled: false

# Poolers
numberPooler:
  enabled: false

stringPooler:
  enabled: false

# Shrinking
lineNumberRemover:
  enabled: false

localVariableRemover:
  enabled: false

removeInnerClasses:
  enabled: false

sourceDebugRemover:
  enabled: false

sourceFileRemover:
  enabled: false

# Shufflers
shuffleFields: false

shuffleMethods: false

shuffleClasses: false
```