package net.minegate.fr.moreblocks.block;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Map;

public class VaseBlock extends Block
{
    private static final   Map<Block, Block> CONTENT_TO_POTTED = Maps.newHashMap();
    protected static final VoxelShape        SHAPE             = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    private final          Block             content;

    public VaseBlock(Block content, AbstractBlock.Settings settings)
    {
        super(settings);
        this.content = content;
        CONTENT_TO_POTTED.put(content, this);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return SHAPE;
    }

    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        Block block = item instanceof BlockItem ? (Block) CONTENT_TO_POTTED.getOrDefault(((BlockItem) item).getBlock(), net.minecraft.block.Blocks.AIR) : net.minecraft.block.Blocks.AIR;
        boolean bl = block == net.minecraft.block.Blocks.AIR;
        boolean bl2 = this.content == net.minecraft.block.Blocks.AIR;
        if (bl != bl2)
        {
            if (bl2)
            {
                world.setBlockState(pos, block.getDefaultState(), 3);
                player.incrementStat(Stats.POT_FLOWER);
                if (!player.getAbilities().creativeMode)
                {
                    itemStack.decrement(1);
                }
            }
            else
            {
                ItemStack itemStack2 = new ItemStack(this.content);
                if (itemStack.isEmpty())
                {
                    player.setStackInHand(hand, itemStack2);
                }
                else if (!player.giveItemStack(itemStack2))
                {
                    player.dropItem(itemStack2, false);
                }
                world.setBlockState(pos, state.getBlock().getDefaultState(), 1);
            }

            return ActionResult.success(world.isClient);
        }
        else
        {
            return ActionResult.CONSUME;
        }
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
    {
        return this.content == net.minecraft.block.Blocks.AIR ? super.getPickStack(world, pos, state) : new ItemStack(this.content);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
    {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public Block getContent()
    {
        return this.content;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
    {
        return false;
    }
}
