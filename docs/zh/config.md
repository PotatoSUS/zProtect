# Configuration Options

## Introduction

zProtect uses the [YAML](https://en.wikipedia.org/wiki/YAML) data serialization language for configuring the behavior of the zProtect obfuscator. It is possible to hand-write the configuration file to upload on the web interface, however, you may wish to use the configurator tool in the zProtect dashboard area.

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

### Output
Name of the obfuscated JAR file.

Value type: [`string`](#string)

### Libraries
Dependencies required to obfuscate your jar to prevent "Not found" errors. 

Value type: [`string array`](#string-array)

### Exclusions
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
Renames various components of your jar's contents.

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

### EnumOptimiser

Value type: [`simple option`](#simple-option)

### FinalRemover

Value type: [`simple option`](#simple-option)

### HideClassMembers

Value type: [`simple option`](#simple-option)

### InsnRemover

Value type: [`simple option`](#simple-option)

### KotlinMetadataRemover

Value type: [`simple option`](#simple-option)

### NOPInsnRemover

Value type: [`simple option`](#simple-option)

### RemoveSignatures

Value type: [`simple option`](#simple-option)

## Poolers

### NumberPooler

Value type: [`simple option`](#simple-option)

### StringPooler

Value type: [`simple option`](#simple-option)

## Shrinking

### LineNumberRemover

Value type: [`simple option`](#simple-option)

### LocalVariableRemover

Value type: [`simple option`](#simple-option)

### RemoveInnerClasses

Value type: [`simple option`](#simple-option)

### SourceDebugRemover

Value type: [`simple option`](#simple-option)

### SourceFileRemover

Value type: [`simple option`](#simple-option)

## Shufflers

### ShuffleFields

Value type: [`simple option`](#simple-option)

### ShuffleMethods

Value type: [`simple option`](#simple-option)

### ShuffleClasses

Value type: [`simple option`](#simple-option)

## Example
Below is a fully functional example configuration file with some zProtect transformers enabled and some disabled.

```yaml
# Blocks debugging options on terminal, may cause issues with certain programs.
AntiDebug:
  enabled: false

# Manipulates instructions to crash decompilers. Currently very buggy at the moment and will likely cause problems.
# Strongly advised not to use this.
DecompilerCrasher:
  enabled: false

# Manipulates annotations to break bad decompilers.
# This should not cause any major issues with Spigot jars.
BadAnnotationCrasher:
  enabled: true

# Adds fake jumps, and such to code.
Flow:
  enabled: false

# Renames various components of your jar's contents.
# Renames class files.
ClassRenamer:
  enabled: false
  path: ""

# Renames field names.
FieldRenamer:
  enabled: false

# Renames local variables.
LocalVariableRenamer:
  enabled: false

# Renames methods.
MethodRenamer:
  enabled: false

# Optimization
EnumOptimiser:
  enabled: false

FinalRemover:
  enabled: false

HideClassMembers:
  enabled: false

InsnRemover:
  enabled: false

KotlinMetadataRemover:
  enabled: false

NOPInsnRemover:
  enabled: false

RemoveSignatures:
  enabled: false

# Poolers
NumberPooler:
  enabled: false

StringPooler:
  enabled: false

# Shrinking
LineNumberRemover:
  enabled: false

LocalVariableRemover:
  enabled: false

RemoveInnerClasses:
  enabled: false

SourceDebugRemover:
  enabled: false

SourceFileRemover:
  enabled: false

# Shufflers
ShuffleFields: false
ShuffleMethods: false
ShuffleClasses: false
```