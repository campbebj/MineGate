package net.minegate.fr.moreblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.block.enums.StairShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class StairsGlassBlock extends StairsBlock
{
    protected StairsGlassBlock(Settings blockSettings)
    {
        super(Blocks.GLASS.getDefaultState(), blockSettings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1)
    {
        if (blockState_2.getBlock() == Blocks.GLASS)
            return true;

        if (blockState_2.getBlock() == net.minegate.fr.moreblocks.block.Blocks.GLASS_SLAB)
            if (isInvisibleToGlassSlab(blockState_1, blockState_2, direction_1))
                return true;

        if (blockState_2.getBlock() == this)
            if (isInvisibleToGlassStairs(blockState_1, blockState_2,
                    direction_1))
                return true;

        return super.isSideInvisible(blockState_1, blockState_2, direction_1);
    }

    private boolean isInvisibleToGlassSlab(BlockState blockState_1, BlockState blockState_2, Direction direction_1)
    {
        BlockHalf half1 = blockState_1.get(StairsBlock.HALF);
        Direction facing1 = blockState_1.get(StairsBlock.FACING);
        StairShape shape1 = blockState_1.get(StairsBlock.SHAPE);
        SlabType type2 = blockState_2.get(SlabBlock.TYPE);

        if (direction_1 == Direction.UP)
            if (type2 != SlabType.TOP)
                return true;

        if (direction_1 == Direction.DOWN)
            if (type2 != SlabType.BOTTOM)
                return true;

        if (type2 == SlabType.DOUBLE)
            return true;

        if (direction_1 == facing1.getOpposite())
            if (type2 == SlabType.BOTTOM && half1 == BlockHalf.BOTTOM)
                return true;
            else if (type2 == SlabType.TOP && half1 == BlockHalf.TOP)
                return true;

        if (direction_1 == facing1.rotateYClockwise()
                && shape1 == StairShape.OUTER_LEFT)
            if (type2 == SlabType.BOTTOM && half1 == BlockHalf.BOTTOM)
                return true;
            else if (type2 == SlabType.TOP && half1 == BlockHalf.TOP)
                return true;

        if (direction_1 == facing1.rotateYCounterclockwise()
                && shape1 == StairShape.OUTER_RIGHT)
            if (type2 == SlabType.BOTTOM && half1 == BlockHalf.BOTTOM)
                return true;
            else return type2 == SlabType.TOP && half1 == BlockHalf.TOP;

        return false;
    }

    private boolean isInvisibleToGlassStairs(BlockState blockState_1, BlockState blockState_2, Direction direction_1)
    {
        BlockHalf half1 = blockState_1.get(StairsBlock.HALF);
        BlockHalf half2 = blockState_2.get(StairsBlock.HALF);
        Direction facing1 = blockState_1.get(StairsBlock.FACING);
        Direction facing2 = blockState_2.get(StairsBlock.FACING);
        StairShape shape1 = blockState_1.get(StairsBlock.SHAPE);
        StairShape shape2 = blockState_2.get(StairsBlock.SHAPE);

        if (direction_1 == Direction.UP)
            if (half2 == BlockHalf.BOTTOM)
                return true;
            else if (half1 != half2)
                if (facing1 == facing2 && shape1 == shape2)
                    return true;
                else
                    switch (shape1)
                    {
                        case STRAIGHT:
                            if (shape2 == StairShape.INNER_LEFT
                                    && (facing2 == facing1
                                    || facing2 == facing1.rotateYClockwise()))
                                return true;
                            else if (shape2 == StairShape.INNER_RIGHT
                                    && (facing2 == facing1 || facing2 == facing1
                                    .rotateYCounterclockwise()))
                                return true;
                            break;

                        case INNER_LEFT:
                            if (shape2 == StairShape.INNER_RIGHT
                                    && facing2 == facing1.rotateYCounterclockwise())
                                return true;
                            break;

                        case INNER_RIGHT:
                            if (shape2 == StairShape.INNER_LEFT
                                    && facing2 == facing1.rotateYClockwise())
                                return true;
                            break;

                        case OUTER_LEFT:
                            if (shape2 == StairShape.OUTER_RIGHT
                                    && facing2 == facing1.rotateYCounterclockwise())
                                return true;
                            else if (shape2 == StairShape.STRAIGHT
                                    && (facing2 == facing1 || facing2 == facing1
                                    .rotateYCounterclockwise()))
                                return true;
                            break;

                        case OUTER_RIGHT:
                            if (shape2 == StairShape.OUTER_LEFT
                                    && facing2 == facing1.rotateYClockwise())
                                return true;
                            else if (shape2 == StairShape.STRAIGHT
                                    && (facing2 == facing1
                                    || facing2 == facing1.rotateYClockwise()))
                                return true;
                            break;
                    }

        if (direction_1 == Direction.DOWN)
            if (half2 == BlockHalf.TOP)
                return true;
            else
                switch (shape1)
                {
                    case STRAIGHT:
                        if (shape2 == StairShape.INNER_LEFT && (facing2 == facing1
                                || facing2 == facing1.rotateYClockwise()))
                            return true;
                        else if (shape2 == StairShape.INNER_RIGHT
                                && (facing2 == facing1
                                || facing2 == facing1.rotateYCounterclockwise()))
                            return true;
                        break;

                    case INNER_LEFT:
                        if (shape2 == StairShape.INNER_RIGHT
                                && facing2 == facing1.rotateYCounterclockwise())
                            return true;
                        break;

                    case INNER_RIGHT:
                        if (shape2 == StairShape.INNER_LEFT
                                && facing2 == facing1.rotateYClockwise())
                            return true;
                        break;

                    case OUTER_LEFT:
                        if (shape2 == StairShape.OUTER_RIGHT
                                && facing2 == facing1.rotateYCounterclockwise())
                            return true;
                        else if (shape2 == StairShape.STRAIGHT && (facing2 == facing1
                                || facing2 == facing1.rotateYCounterclockwise()))
                            return true;
                        break;

                    case OUTER_RIGHT:
                        if (shape2 == StairShape.OUTER_LEFT
                                && facing2 == facing1.rotateYClockwise())
                            return true;
                        else if (shape2 == StairShape.STRAIGHT && (facing2 == facing1
                                || facing2 == facing1.rotateYClockwise()))
                            return true;
                        break;
                }

        if (facing2 == direction_1.getOpposite())
            return true;

        if (direction_1 == facing1)
            if (half1 == half2 && shape1 != StairShape.STRAIGHT)
                if (facing2 == facing1.rotateYCounterclockwise()
                        && shape2 != StairShape.OUTER_RIGHT)
                    return true;
                else if (facing2 == facing1.rotateYClockwise()
                        && shape2 != StairShape.OUTER_LEFT)
                    return true;

        if (direction_1 == facing1.getOpposite())
            if (half1 == half2)
                if (facing2 == facing1.rotateYCounterclockwise()
                        && shape2 != StairShape.OUTER_LEFT)
                    return true;
                else if (facing2 == facing1.rotateYClockwise()
                        && shape2 != StairShape.OUTER_RIGHT)
                    return true;

        if (direction_1 == facing1.rotateYCounterclockwise())
            if (half1 == half2)
                if (facing2 == direction_1 && shape1 != StairShape.INNER_LEFT
                        && shape2 == StairShape.INNER_RIGHT)
                    return true;
                else if (facing2 == facing1 && shape2 != StairShape.OUTER_LEFT)
                    return true;
                else if (facing2 == facing1.getOpposite()
                        && shape1 == StairShape.OUTER_RIGHT)
                    return true;

        if (direction_1 == facing1.rotateYClockwise())
            if (half1 == half2)
                if (facing2 == direction_1 && shape1 != StairShape.INNER_RIGHT
                        && shape2 == StairShape.INNER_LEFT)
                    return true;
                else if (facing2 == facing1 && shape2 != StairShape.OUTER_RIGHT)
                    return true;
                else return facing2 == facing1.getOpposite()
                            && shape1 == StairShape.OUTER_LEFT;

        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockState blockState_1,
                                               BlockView blockView_1, BlockPos blockPos_1)
    {
        return 1.0F;
    }

    @Override
    public boolean isTranslucent(BlockState blockState_1, BlockView blockView_1,
                                 BlockPos blockPos_1)
    {
        return true;
    }
}