import{_ as p,o as t,c,a as e,b as a,w as d,e as s,d as o,r as n}from"./app.fe610a35.js";const h={},m=e("h1",{id:"configuration-options",tabindex:"-1"},[e("a",{class:"header-anchor",href:"#configuration-options","aria-hidden":"true"},"#"),s(" Configuration Options")],-1),u=e("h2",{id:"introduction",tabindex:"-1"},[e("a",{class:"header-anchor",href:"#introduction","aria-hidden":"true"},"#"),s(" Introduction")],-1),D=s("zProtect uses the "),v={href:"https://en.wikipedia.org/wiki/YAML",target:"_blank",rel:"noopener noreferrer"},y=s("YAML"),b=s(" data serialization language for configuring the behavior of the zProtect obfuscator. It is possible to hand-write the configuration file to upload on the web interface, however, you may wish to modify the "),f=e("a",{href:"#example"},"example file",-1),C=s(" or generate one using the "),g=s("command line tool"),x=s("."),_=o(`<h2 id="value-types" tabindex="-1"><a class="header-anchor" href="#value-types" aria-hidden="true">#</a> Value Types</h2><p>There are a few value types used in the zProtect configuration, which are listed below.</p><h3 id="boolean" tabindex="-1"><a class="header-anchor" href="#boolean" aria-hidden="true">#</a> Boolean</h3><p>Only accepts <code>true</code> and <code>false</code> as the value.</p><p>For example:</p><div class="language-yaml ext-yml line-numbers-mode"><pre class="shiki" style="background-color:#1E1E1E;"><code><span class="line"><span style="color:#6A9955;"># A key with value as true</span></span>
<span class="line"><span style="color:#569CD6;">key1</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A9955;"># A key with value as false</span></span>
<span class="line"><span style="color:#569CD6;">key2</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="string" tabindex="-1"><a class="header-anchor" href="#string" aria-hidden="true">#</a> String</h3><p>Accepts any text character.</p><p>For example:</p><div class="language-yaml ext-yml line-numbers-mode"><pre class="shiki" style="background-color:#1E1E1E;"><code><span class="line"><span style="color:#6A9955;"># A key with a string value</span></span>
<span class="line"><span style="color:#569CD6;">key</span><span style="color:#D4D4D4;">: </span><span style="color:#CE9178;">value</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="string-array" tabindex="-1"><a class="header-anchor" href="#string-array" aria-hidden="true">#</a> String Array</h3><p>A list of <a href="#string"><code>strings</code></a>, separated by a newline and prefixed with a dash.</p><p>For example:</p><div class="language-yaml ext-yml line-numbers-mode"><pre class="shiki" style="background-color:#1E1E1E;"><code><span class="line"><span style="color:#6A9955;"># An array with entries</span></span>
<span class="line"><span style="color:#569CD6;">key</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">- </span><span style="color:#CE9178;">something</span></span>
<span class="line"><span style="color:#D4D4D4;">- </span><span style="color:#CE9178;">another thing</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A9955;"># An empty array</span></span>
<span class="line"><span style="color:#569CD6;">empty</span><span style="color:#D4D4D4;">: []</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="simple-option" tabindex="-1"><a class="header-anchor" href="#simple-option" aria-hidden="true">#</a> Simple Option</h3><p>A <a href="#string-array"><code>string array</code></a> with a <a href="#boolean"><code>boolean</code></a> value <code>enabled</code>.</p><p>For example:</p><div class="language-yaml ext-yml line-numbers-mode"><pre class="shiki" style="background-color:#1E1E1E;"><code><span class="line"><span style="color:#6A9955;"># Option enabled</span></span>
<span class="line"><span style="color:#569CD6;">key</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">true</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="option-with-path" tabindex="-1"><a class="header-anchor" href="#option-with-path" aria-hidden="true">#</a> Option with Path</h3><p>A <a href="#string-array"><code>string array</code></a> with a <a href="#boolean"><code>boolean</code></a> key <code>enabled</code> and a <a href="#string"><code>string</code></a> key <code>path</code>.</p><p>For example:</p><div class="language-yaml ext-yml line-numbers-mode"><pre class="shiki" style="background-color:#1E1E1E;"><code><span class="line"><span style="color:#6A9955;"># Option enabled</span></span>
<span class="line"><span style="color:#569CD6;">key</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">true</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">path</span><span style="color:#D4D4D4;">: </span><span style="color:#CE9178;">something</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="options" tabindex="-1"><a class="header-anchor" href="#options" aria-hidden="true">#</a> Options</h2><p>These options specify runtime information and general configuration.</p><h3 id="libraries" tabindex="-1"><a class="header-anchor" href="#libraries" aria-hidden="true">#</a> libraries</h3><p>Dependencies required to obfuscate your jar to prevent &quot;Not found&quot; errors.</p><p>Value type: <a href="#string-array"><code>string array</code></a></p><h3 id="exclusions" tabindex="-1"><a class="header-anchor" href="#exclusions" aria-hidden="true">#</a> exclusions</h3><p>Class files and directories that should be ignored by the obfuscator. They will not have any obfuscation applied to them.</p><p>Value type: <a href="#string-array"><code>string array</code></a></p><h3 id="forceexclusions" tabindex="-1"><a class="header-anchor" href="#forceexclusions" aria-hidden="true">#</a> ForceExclusions</h3><div class="custom-container tip"><p class="custom-container-title">Note</p><p>Transformer priority will fix this in the future.</p></div><p>Class files and directories that should be blacklisted by the obfuscator before processing. This will prevent issues with exclusions if the regular exclusions fail for some unknown reason.</p><p>Value type: <a href="#string-array"><code>string array</code></a></p><h3 id="watermark" tabindex="-1"><a class="header-anchor" href="#watermark" aria-hidden="true">#</a> Watermark</h3><p>Adds a watermark to the obfuscated JAR for advertisement. Only removable for users with a Commercial License.</p><p>Value type: <a href="#boolean"><code>boolean</code></a></p><h2 id="transformers" tabindex="-1"><a class="header-anchor" href="#transformers" aria-hidden="true">#</a> Transformers</h2><p>These options specify wherether to enable available obfuscation techniques.</p>`,39),k={id:"antidebug",tabindex:"-1"},R=e("a",{class:"header-anchor",href:"#antidebug","aria-hidden":"true"},"#",-1),w=s(" AntiDebug "),V=e("div",{class:"custom-container tip"},[e("p",{class:"custom-container-title"},"Warning!"),e("p",null,"This option may cause issues with certain programs.")],-1),A=e("p",null,"Blocks debugging options on terminal.",-1),E=e("p",null,[s("Value type: "),e("a",{href:"#simple-option"},[e("code",null,"simple option")])],-1),S={id:"decompilercrasher",tabindex:"-1"},M=e("a",{class:"header-anchor",href:"#decompilercrasher","aria-hidden":"true"},"#",-1),F=s(" DecompilerCrasher "),N=o(`<div class="custom-container tip"><p class="custom-container-title">Warning!</p><p>This option is very buggy at the moment and will likely cause problems. You are strongly advised not to use this.</p></div><p>Manipulates instructions to crash decompilers.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="badannotationcrasher" tabindex="-1"><a class="header-anchor" href="#badannotationcrasher" aria-hidden="true">#</a> BadAnnotationCrasher</h3><p>Manipulates annotations to break bad decompilers. This should not cause any major issues.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="flow" tabindex="-1"><a class="header-anchor" href="#flow" aria-hidden="true">#</a> Flow</h3><p>Adds fake jumps, and such to code.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h2 id="renamers" tabindex="-1"><a class="header-anchor" href="#renamers" aria-hidden="true">#</a> Renamers</h2><p>Renames various components of your JAR&#39;s contents.</p><h3 id="classrenamer" tabindex="-1"><a class="header-anchor" href="#classrenamer" aria-hidden="true">#</a> ClassRenamer</h3><p>Renames class files.</p><p>Value type: <a href="#option-with-path"><code>option with path</code></a></p><h3 id="fieldrenamer" tabindex="-1"><a class="header-anchor" href="#fieldrenamer" aria-hidden="true">#</a> FieldRenamer</h3><p>Renames field names.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="localvariablerenamer" tabindex="-1"><a class="header-anchor" href="#localvariablerenamer" aria-hidden="true">#</a> LocalVariableRenamer</h3><p>Renames local variables.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="methodrenamer" tabindex="-1"><a class="header-anchor" href="#methodrenamer" aria-hidden="true">#</a> MethodRenamer</h3><p>Renames methods.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h2 id="optimization" tabindex="-1"><a class="header-anchor" href="#optimization" aria-hidden="true">#</a> Optimization</h2><p>Modifies or removes various aspects of your jar file to make debugging harder.</p><h3 id="enumoptimiser" tabindex="-1"><a class="header-anchor" href="#enumoptimiser" aria-hidden="true">#</a> EnumOptimiser</h3><p>Removes all clone call(s) and returns an array to optimize enum values.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="finalremover" tabindex="-1"><a class="header-anchor" href="#finalremover" aria-hidden="true">#</a> FinalRemover</h3><p>Removes finals from your code.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="hideclassmembers" tabindex="-1"><a class="header-anchor" href="#hideclassmembers" aria-hidden="true">#</a> HideClassMembers</h3><p>Mark classes as synthetic to hide them from bad decompilers.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="insnremover" tabindex="-1"><a class="header-anchor" href="#insnremover" aria-hidden="true">#</a> InsnRemover</h3><p>Removes the instructions <code>const_.</code> and <code>tableswitch</code>.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="kotlinmetadataremover" tabindex="-1"><a class="header-anchor" href="#kotlinmetadataremover" aria-hidden="true">#</a> KotlinMetadataRemover</h3><p>Removes Kotlin-specific Metadata such as <code>NotNull</code>, <code>Nullable</code>, etc.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="nopinsnremover" tabindex="-1"><a class="header-anchor" href="#nopinsnremover" aria-hidden="true">#</a> NOPInsnRemover</h3><p>Removes extended type information.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="removesignatures" tabindex="-1"><a class="header-anchor" href="#removesignatures" aria-hidden="true">#</a> RemoveSignatures</h3><p>Removes the signature attribute from classes and methods.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h2 id="poolers" tabindex="-1"><a class="header-anchor" href="#poolers" aria-hidden="true">#</a> Poolers</h2><p>Move values into an array to make the resulting JAR harder.</p><h3 id="numberpooler" tabindex="-1"><a class="header-anchor" href="#numberpooler" aria-hidden="true">#</a> NumberPooler</h3><p>Moves numbers into an array.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="stringpooler" tabindex="-1"><a class="header-anchor" href="#stringpooler" aria-hidden="true">#</a> StringPooler</h3><p>Moves strings into an array.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h2 id="shrinking" tabindex="-1"><a class="header-anchor" href="#shrinking" aria-hidden="true">#</a> Shrinking</h2><p>Remove various attributes to make it harder to understand the resulting JAR.</p><h3 id="linenumberremover" tabindex="-1"><a class="header-anchor" href="#linenumberremover" aria-hidden="true">#</a> LineNumberRemover</h3><p>Removes line numbers so StackTraces show <code>(Unknown)</code> on errors.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="localvariableremover" tabindex="-1"><a class="header-anchor" href="#localvariableremover" aria-hidden="true">#</a> LocalVariableRemover</h3><p>Removes the local variable attribute from methods.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="removeinnerclasses" tabindex="-1"><a class="header-anchor" href="#removeinnerclasses" aria-hidden="true">#</a> RemoveInnerClasses</h3><p>Removes various attributes such as <code>outerClass</code>, <code>outerMethod</code>, etc. from the class.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="sourcedebugremover" tabindex="-1"><a class="header-anchor" href="#sourcedebugremover" aria-hidden="true">#</a> SourceDebugRemover</h3><p>Removes the <code>SourceDebug</code> attribute from the class.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="sourcefileremover" tabindex="-1"><a class="header-anchor" href="#sourcefileremover" aria-hidden="true">#</a> SourceFileRemover</h3><p>Removes the <code>SourceFile</code> attribute from the class.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h2 id="shufflers" tabindex="-1"><a class="header-anchor" href="#shufflers" aria-hidden="true">#</a> Shufflers</h2><p>Randomly shuffles various elements of your jar file to make it more difficult to find them.</p><h3 id="shufflefields" tabindex="-1"><a class="header-anchor" href="#shufflefields" aria-hidden="true">#</a> ShuffleFields</h3><p>Randomly shuffles elements in the mutable list of fields.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="shufflemethods" tabindex="-1"><a class="header-anchor" href="#shufflemethods" aria-hidden="true">#</a> ShuffleMethods</h3><p>Randomly shuffles elements in the mutable list of methods.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h3 id="shuffleclasses" tabindex="-1"><a class="header-anchor" href="#shuffleclasses" aria-hidden="true">#</a> ShuffleClasses</h3><p>Randomly shuffles elements in the mutable list of classes.</p><p>Value type: <a href="#simple-option"><code>simple option</code></a></p><h2 id="example" tabindex="-1"><a class="header-anchor" href="#example" aria-hidden="true">#</a> Example</h2><p>Below is a fully functional example configuration file with some zProtect transformers enabled and some disabled.</p><div class="language-yaml ext-yml line-numbers-mode"><pre class="shiki" style="background-color:#1E1E1E;"><code><span class="line"><span style="color:#6A9955;"># zProtect Configuration File Version 1</span></span>
<span class="line"><span style="color:#6A9955;"># Reference: https://docs.zprotect.dev/config.html</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A9955;"># May cause issues with certain programs.</span></span>
<span class="line"><span style="color:#569CD6;">antiDebug</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A9955;"># Strongly advised not to use this.</span></span>
<span class="line"><span style="color:#569CD6;">decompilerCrasher</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">badAnnotationCrasher</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">flow</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">classRenamer</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">path</span><span style="color:#D4D4D4;">: </span><span style="color:#CE9178;">&quot;&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">fieldRenamer</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">localVariableRenamer</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">methodRenamer</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A9955;"># Optimization</span></span>
<span class="line"><span style="color:#569CD6;">enumOptimiser</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">finalRemover</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">hideClassMembers</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">insnRemover</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">kotlinMetadataRemover</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">NOPInsnRemover</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">removeSignatures</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A9955;"># Poolers</span></span>
<span class="line"><span style="color:#569CD6;">numberPooler</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">stringPooler</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A9955;"># Shrinking</span></span>
<span class="line"><span style="color:#569CD6;">lineNumberRemover</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">localVariableRemover</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">removeInnerClasses</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">sourceDebugRemover</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">sourceFileRemover</span><span style="color:#D4D4D4;">:</span></span>
<span class="line"><span style="color:#D4D4D4;">  </span><span style="color:#569CD6;">enabled</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A9955;"># Shufflers</span></span>
<span class="line"><span style="color:#569CD6;">shuffleFields</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">shuffleMethods</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#569CD6;">shuffleClasses</span><span style="color:#D4D4D4;">: </span><span style="color:#569CD6;">false</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div>`,85);function O(P,T){const i=n("ExternalLinkIcon"),r=n("RouterLink"),l=n("Badge");return t(),c("div",null,[m,u,e("p",null,[D,e("a",v,[y,a(i)]),b,f,C,a(r,{to:"/commandline.html"},{default:d(()=>[g]),_:1}),x]),_,e("h3",k,[R,w,a(l,{type:"warning",text:"incompatibilities",vertical:"top"})]),V,A,E,e("h3",S,[M,F,a(l,{type:"danger",text:"issues",vertical:"top"})]),N])}var L=p(h,[["render",O],["__file","config.html.vue"]]);export{L as default};
